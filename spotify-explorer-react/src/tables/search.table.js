import React from 'react';

export default class SearchTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: true,
            items: []
        };
        this.searchFormSubject = props.searchFormSubject;
        this.searchFormSubject.subscribe((form) => this.getAlbums(form));
        this.spotifyService = props.spotifyService;
        this.spotifyService.albumsSubject.subscribe(result => this.setAlbumsItems(result));
        this.spotifyService.albumsErrorSubject.subscribe(error => this.setError(error));
    }
    getAlbums(form) {
        if (form && form.q) {
            this.setState({
                isLoaded: false,
                items: []
            });
            this.spotifyService.getAlbums(form.q);
        }
    }
    setAlbumsItems(result) {
        if (result) {
            this.setState({
                isLoaded: true,
                items: result.albums.items
            });
        }
    }
    setError(error) {
        if (error) {
            this.setState({
                isLoaded: false,
                error: error.message
            });
        }
    }
    render() {
        const { error, isLoaded, items } = this.state;
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-sm-12">
                        <table className="table ">
                            <thead className="thead-dark">
                                <tr>
                                    <th scope="col">Image</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Artists</th>
                                    <th scope="col">Release Date</th>
                                    <th scope="col">Total tracks</th>
                                </tr>
                            </thead>
                            <tbody>
                                {error && <tr><td colSpan="5">Error: {error}</td></tr>}
                                {!error && !isLoaded && <tr><td>Loading...</td></tr>}
                                {items.map(item => (
                                    <tr>
                                        <th scope="row"><img src={item.images[2].url} alt="{item.name} Album" /></th>
                                        <td>{item.name}</td>
                                        <td>{item.artists.map(artist => (artist.name)).join(', ')}</td>
                                        <td>{item.release_date}</td>
                                        <td>{item.total_tracks}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );

    }
}
