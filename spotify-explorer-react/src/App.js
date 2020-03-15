import React from 'react';
import SearchPage from './pages/search.page.js';
import SpotifyService from './services/spotify.service.js';

function App() {
  let spotifyService = new SpotifyService();  
  return (
    <SearchPage spotifyService={spotifyService} />
  );
}

export default App;