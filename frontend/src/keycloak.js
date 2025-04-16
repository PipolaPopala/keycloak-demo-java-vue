import Keycloak from 'keycloak-js'

const initOptions = {
  url: 'http://localhost:8080',
  realm: 'test_realm',
  clientId: 'app_client',
  onLoad: 'check-sso',
  silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
  pkceMethod: 'S256',
}

const keycloak = new Keycloak(initOptions)

export default keycloak