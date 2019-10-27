/*global google*/
import React from "react"
import { compose, withProps, withHandlers, withState } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow, Circle } from "react-google-maps"
import CollectService from './services/CollectService';

const MyMapComponent = compose(
  withProps({
    googleMapURL: `https://maps.googleapis.com/maps/api/js?key=${
      process.env.REACT_APP_GOOGLE_KEY
      }&libraries=drawing,places`,
    loadingElement: <div style={{ height: `100%` }} />,
    containerElement: <div style={{ height: '100vh', width: '100%' }} />,
    mapElement: <div style={{ height: `100%` }} />
  }),
  withScriptjs,
  withGoogleMap,
  withState('places', 'updatePlaces', ''),
  withHandlers(() => {
    const refs = {
      map: undefined,
    }

    return {
      onMapMounted: () => ref => {
        refs.map = ref
      },
      fetchPlaces: ({ updatePlaces }) => {

        const service = new google.maps.places.PlacesService(refs.map.context.__SECRET_MAP_DO_NOT_USE_OR_YOU_WILL_BE_FIRED);
        const request = {
          //bounds: bounds,
          location: new google.maps.LatLng(refs.map.props.defaultCenter.lat, refs.map.props.defaultCenter.lng),
          radius: 900,
          fields: ['formatted_phone_number', 'opening_hours'],
          name: ['Itaú']
        };

        service.nearbySearch(request, (results, status) => {
          if (status === google.maps.places.PlacesServiceStatus.OK) {
            updatePlaces(results);
          }
        })
      }
    }
  }),
)((props) => {

  return (
    <GoogleMap
      defaultClickableIcons={false}
      onTilesLoaded={props.fetchPlaces}
      ref={props.onMapMounted}
      onBoundsChanged={props.fetchPlaces}
      defaultZoom={16}
      defaultCenter={props.currentLocation}
    >
      {props.places && props.places.map((place, i) =>
        <Marker key={i} position={{ lat: place.geometry.location.lat(), lng: place.geometry.location.lng() }}
          onClick={() => props.handleToggleOpen(place, new google.maps.places.PlacesService(document.createElement('div')))}>
          {(props.showInfo === place) &&
            <InfoWindow>
              <div>
                <div dangerouslySetInnerHTML={{ __html: props.showResumo }}>
                </div>
              </div>
            </InfoWindow>}
        </Marker>
      )}

      <Marker
        icon="https://www.robotwoods.com/dev/misc/bluecircle.png"
        position={props.currentLocation}
      />

      <Circle center={props.currentLocation} radius={900} options={{ fillColor: 'red', strokeColor: 'red' }} />
    </GoogleMap>
  )
})

export default class App extends React.PureComponent {

  constructor(props) {
    super(props)
    this.state = {
      currentLatLng: {
        lat: -23.4729319,
        lng: -46.6025683,
      },
      showInfo: null,
      showResumo: ''
    }
  }

  showCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          this.setState(prevState => ({
            currentLatLng: {
              ...prevState.currentLatLng,
              lat: position.coords.latitude,
              lng: position.coords.longitude
            }
          }))
        }
      )
    }
  }

  handleToggleOpen = (info, service) => {

    service.getDetails({
      placeId: info.place_id,
      fields: ['formatted_phone_number', 'opening_hours', 'plus_code', 'address_component', 'reviews', 'geometry']
    }, (place, status) => {


      this.handleCollect({
        lat: info.geometry.location.lat(),
        lng: info.geometry.location.lng(),
        placeId: info.place_id
      }).then(ag => {

        let result = 'Agência: ' + ag;
        result += '<ul>';
        if (status === google.maps.places.PlacesServiceStatus.OK) {
          for (var i = 0; i < place.opening_hours.weekday_text.length; i++) {
            result += '<li>' + place.opening_hours.weekday_text[i] + '</li>';
          }
        }
        result += '</ul>'

        this.setState((prevState => ({
          showInfo: info,
          showResumo: result
        })))
      })

    })

  }

  componentDidMount() {
    this.showCurrentLocation();

    window.localStorage.setItem('apiUrl', 'http://192.168.1.103:8080');

  }

  async handleCollect(request) {
    const {
      response: collectResponse,
      statusCode: collectStatusCode,
      errorMessage: collectErrorMessage,
    } = await CollectService.save(request)

    if( collectStatusCode === 500) return '** Não foi encontrada o número dessa agência **';

    return collectResponse.result.agency;
  }

  render() {
    return (
      <MyMapComponent {...this.state} currentLocation={this.state.currentLatLng} handleToggleOpen={this.handleToggleOpen} />
    )
  }
}