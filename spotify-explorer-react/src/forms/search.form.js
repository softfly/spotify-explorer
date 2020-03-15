import React from 'react';

export default class SearchForm extends React.Component {
    constructor(props) {
      super(props);
      this.handleKeyUp = this.handleKeyUp.bind(this);
      this.q = React.createRef();
      this.searchFormSubject = props.searchFormSubject;
    }
    handleKeyUp(event) {
      this.searchFormSubject.next({q: this.q.current.value});
    }
    render() {
      return <div className="container-fluid mb-2 mt-2">
        <div className="row">
          <div className="col-sm-12">
            <form>
              <div className="row">
                <div className="col">
                  <input type="text" className="form-control" placeholder="Search" ref={this.q} onKeyUp={this.handleKeyUp} />
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>;
    }
  }