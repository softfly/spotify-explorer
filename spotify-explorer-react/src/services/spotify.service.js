
import { BehaviorSubject } from 'rxjs';

export default class SpotifyService {
    constructor() {
        this.albumsSubject = new BehaviorSubject();
        this.albumsErrorSubject = new BehaviorSubject();
    }
    getAlbums(q) {
        fetch("/api/spotify/v1/search?q=" + q)
            .then(response => {
                if (response.ok) {
                    response.json().then(r => this.albumsSubject.next(r)).catch(e => this.albumsErrorSubject.next(e));
                } else {
                    response.json().then(e => this.albumsErrorSubject.next(e)).catch(e => this.albumsErrorSubject.next(e));
                }
            })
            .catch(e => this.albumsErrorSubject.next(e));
    }
}