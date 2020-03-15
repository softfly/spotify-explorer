import React from 'react';
import { Header } from './../Header';
import SearchForm from './../forms/search.form.js';
import SearchTable from './../tables/search.table.js';
import { BehaviorSubject } from 'rxjs';

export default class SearchPage extends React.Component {
    constructor(props) {
      super(props);
      this.searchFormSubject = new BehaviorSubject();
      this.spotifyService = props.spotifyService;
    }
    render() {
        return <div className="App">
            {Header()}
            <SearchForm searchFormSubject={this.searchFormSubject} />
            <SearchTable searchFormSubject={this.searchFormSubject} spotifyService={this.spotifyService} />
          </div>;
    }
  }