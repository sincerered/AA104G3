class Calendar extends React.Component {
  constructor(props) {
    super(props);
    this.selectedColor = 'rgb(200, 50, 200)';
    this.unselectedColor = 'rgb(255, 255, 255)';
    this.unavailableColor = 'rgb(100, 100, 100)';

    this.minDateLong = 11111111111;
    this.maxDateLong = 13222222221;
    this.minDate = new Date(this.minDateLong);
    this.maxDate = new Date(this.maxDateLong);
    this.minDateDetail = {
      year: this.minDate.getFullYear(),
      month: this.minDate.getMonth(),
      date: this.minDate.getDate(),
      hour: this.minDate.getHours(),
    };
    this.maxDateDetail = {
      year: this.maxDate.getFullYear(),
      month: this.maxDate.getMonth(),
      date: this.maxDate.getDate(),
    };

    this.weeks = ["日", "一", "二", "三", "四", "五", "六"];
    this.weekBar = this.weeks.map(d => <div className="gab-calendar-unit">{d}</div>);

    this.monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    this.monthWord = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December'
    ];

    this.getMonthFirstDate = this.getMonthFirstDate.bind(this);
    this.getMonthDates = this.getMonthDates.bind(this);
    this.getDatePanel = this.getDatePanel.bind(this);
    this.getBlankPanel = this.getBlankPanel.bind(this);
    this.handleMonthDelta = this.handleMonthDelta.bind(this);
    this.handleDateSelect = this.handleDateSelect.bind(this);
    this.showTimeSeat = this.showTimeSeat.bind(this);
    this.showSeatPanel = this.showSeatPanel.bind(this);
    this.showTimePanel = this.showTimePanel.bind(this);
    this.handleScroll = this.handleScroll.bind(this);
    this.showTimePanelCell = this.showTimePanelCell.bind(this);
    this.handleTimeClick = this.handleTimeClick.bind(this);

    this.state = {
      booking: [],
      year: this.minDate.getFullYear(),
      month: this.minDate.getMonth(),
      date: this.minDate.getDate(),
      day: this.minDate.getDay(),
      selectedDate: {year: '\u00a0', month: '', date: '', day: ''},
      isTimeSelected: false,
      isDateSelected: false,
      timeSelectedCol: '',
      timeSelectedMin: '',
      timeSelectedMax: '',

    };
  }
  
  getMonthFirstDate(year, month) {
    var newDate = new Date(year, month, 1);
    return newDate.getDay();
  }

  getBlankPanel(year, month) {
    var newDate = new Date(year, month, 1);
    var firstDay =  newDate.getDay();
    var arrayTemp = [];
    for(var i = 0; i < firstDay; i++){
      arrayTemp.push('\u00a0');
    }
    return arrayTemp.map(a => <div className="gab-calendar-unit">{a}</div>);
  }

  getDatePanel(year, month) {
    var minDate = 0;
    var maxDate = 32;
    if(month == this.minDateDetail.month && year == this.minDateDetail.year){
      minDate = this.minDateDetail.date;
    }
    if(month == this.maxDateDetail.month && year == this.maxDateDetail.year){
      maxDate = this.maxDateDetail.date;
    }
    if(this.props.selectingSeat && this.props.selectingSeat != ''){
      var tmp = year + month;
      var selectingSeat = {};
      var allSelectingSeat = this.props.selectingSeat;
      for(var date in allSelectingSeat){
        if(date.substr(0, 6) == tmp){
          selectingSeat[date.substr(6, 2)] = allSelectingSeat[date]; 
        }
      }
    }

    var dateArrayTemp = [];
    for(var i = 0; i < this.getMonthDates(year, month); i++){
      dateArrayTemp.push(i + 1);
    }
    console.log("calendar render run");
    return dateArrayTemp.map(a => {
      if(a <= minDate || a > maxDate){
        return <div className="gab-calendar-unit" style={{backgroundColor: '#ccc'}}>{a}</div>
      }
      

      if(a == this.state.selectedDate.date){
        if(month == this.state.selectedDate.month && year == this.state.selectedDate.year){
          return <div className="gab-calendar-unit" onMouseDown={this.handleDateSelect} style={{backgroundColor: 'red'}}>{a}</div>
        }
      }
      return <div className="gab-calendar-unit" onMouseDown={this.handleDateSelect}>{a}</div>
    });
    
  }

  getMonthDates(year, month) {
    if(month == 1){
      if(year % 400 == 0){
        return 29;
      }else if(year % 100 == 0){
        return 28;
      }else if(year % 4 == 0){
        return 29;
      }
    }
    return this.monthDays[month];
  }

  showTimeSeat() {
    console.log('timeSeat render');
    var minSeat = this.props.storeData.minSeat;
    var maxSeat = this.props.storeData.maxSeat;
    var minTime = this.props.storeData.minTime;
    var maxTime = this.props.storeData.maxTime;
    return (
      <table className="table chenken-table text-center">
        <thead>
          <tr style={{position: 'absolute'}}>{this.showSeatPanel(minSeat, maxSeat)}</tr>
          <tr>{this.showSeatPanel(minSeat, maxSeat)}</tr>
        </thead>
        <tbody>
          {this.showTimePanel(minTime, maxTime, minSeat, maxSeat)}
        </tbody>
      </table>
    );
  }

  showSeatPanel(minSeat, maxSeat){
    var arr = [];
    for(var i = minSeat; i <= maxSeat; i++){
      arr.push(i + '位');
    }
    return arr.map(a => <th>{a}</th>);
  }

  showTimePanel(minTime, maxTime, minSeat, maxSeat){
    console.log('showtimepanel');
    var seatCount = maxSeat - minSeat + 1;
    var arr = [];
    for(var i = minTime; i <= maxTime; i++){
      arr.push(i);
    }
    if(this.state.selectedDate.year == '\u00a0'){
      return arr.map(time => <tr>{this.showTimePanelCell(time, seatCount)}</tr>);
    }else{
      console.log('showtimepanel year is not u00a0');
      var selectedDate = this.state.selectedDate.year + ('0' + (this.state.selectedDate.month + 1)).slice(-2) + ('0' + this.state.selectedDate.date).slice(-2);
      var fullSeat = this.props.storeData.seat[selectedDate];
      console.log(this.state.selectedDate);
      console.log(this.props.storeData.seat);
      return arr.map((time, index) => <tr>{this.showTimePanelCell(time, seatCount, fullSeat[index])}</tr>);
    }
    
  }

  showTimePanelCell(time, seatCount, fullSeat){
    var arr = [];
    if(fullSeat === undefined){
      for(var i = 0; i < seatCount; i++){
        arr.push(<td style={{'background-color': '#fff'}} data-col={i} data-time={time} onClick={this.handleTimeClick}>{time}:00</td>);
      }
      
    }
    else{

      for(var i = 0; i < seatCount; i++){
        arr.push(
          fullSeat[i] == '1'? <td style={{'background-color': this.unselectedColor}} data-col={i} data-time={time} onClick={this.handleTimeClick}>{time}:00</td>: <td style={{'background-color': this.unavailableColor}} data-col={i} data-time={time}>{time}:00</td>
        );
      }
    }
    return arr;
  }
  
  handleMonthDelta(delta, e) {
    var month = this.state.month;
    var year = this.state.year;
    if(year == this.minDateDetail.year && month == this.minDateDetail.month && delta == -1){
      return;
    }else if(year == this.maxDateDetail.year && month == this.maxDateDetail.month && delta == 1){
      return;
    }
    if(month == 0){
      if(delta == -1){
        month = 12
        year = this.state.year - 1;
      }
    }else if(month == 11){
      if(delta == 1){
        month = -1;
        year = this.state.year + 1;
      }
    }
    month += delta;
    if(month == this.state.selectedDate.month && year == this.state.selectedDate.year){
      e.target.parentNode.parentNode.lastChild.childNodes[this.getMonthFirstDate(this.state.year, this.state.month) + this.state.selectedDate.date - 1].style.backgroundColor = 'red';
    }else{
      e.target.parentNode.parentNode.lastChild.childNodes[this.getMonthFirstDate(this.state.year, this.state.month) + this.state.selectedDate.date - 1].style.backgroundColor = '#fff';
    }
    this.setState({year: year, month: month});
  }

  handleTimeClick(e){
    var target = e.target;
    var col = parseInt(target.dataset.col);
    var timeSelect = parseInt(target.dataset.time);
    if(target.style.backgroundColor == this.unselectedColor){
      if(this.state.isTimeSelected){
        if(col == this.state.timeSelectedCol){
          if(timeSelect < this.state.timeSelectedMin){
            var oldTimeSelectedMin = this.state.timeSelectedMin;
            this.state.timeSelectedMin = parseInt(target.dataset.time);
            while(parseInt(target.dataset.time) < oldTimeSelectedMin){
              target.style.backgroundColor = this.selectedColor;
              target = target.parentNode.nextSibling.children[col];
              if(target.style.backgroundColor == this.unavailableColor){
                var oldTimeSelectedMax = this.state.timeSelectedMax;
                this.state.timeSelectedMax = this.state.timeSelectedMin;
                target = target.parentNode.parentNode.children[oldTimeSelectedMax - this.props.storeData.minTime].children[col]
                while(parseInt(target.dataset.time) > this.state.timeSelectedMin){
                  if(target.style.backgroundColor == this.selectedColor){
                    target.style.backgroundColor = this.unselectedColor;
                  }
                  target = target.parentNode.previousSibling.children[col];
                }
                console.log(this.state.timeSelectedMin + '-' + this.state.timeSelectedMax);
                return;
              }
            }
          } // time < min
          else if(timeSelect > this.state.timeSelectedMax){
            var oldTimeSelectedMax = this.state.timeSelectedMax;
            this.state.timeSelectedMax = parseInt(target.dataset.time);
            while(parseInt(target.dataset.time) > oldTimeSelectedMax){
              target.style.backgroundColor = this.selectedColor;
              target = target.parentNode.previousSibling.children[col];
              if(target.style.backgroundColor == this.unavailableColor){
                var oldTimeSelectedMin = this.state.timeSelectedMin;
                this.state.timeSelectedMin = this.state.timeSelectedMax;

                target = target.parentNode.parentNode.children[oldTimeSelectedMin - this.props.storeData.minTime].children[col]

                while(parseInt(target.dataset.time) < this.state.timeSelectedMax){
                  if(target.style.backgroundColor == this.selectedColor){
                    target.style.backgroundColor = this.unselectedColor;
                  }
                  target = target.parentNode.nextSibling.children[col];
                }
                console.log(this.state.timeSelectedMin + '-' + this.state.timeSelectedMax);

                return;
              }
            }
          } //time > max
        }              //col == this.state.timeSelectedCol
        else{
          var tbody = target.parentNode.parentNode;
          var timeSelectedMaxIndex = this.state.timeSelectedMax - this.props.storeData.minTime;
          var timeSelectedMinIndex = this.state.timeSelectedMin - this.props.storeData.minTime;
          var oldCol = this.state.timeSelectedCol;
          for(var i = timeSelectedMinIndex; i <= timeSelectedMaxIndex; i++){
            tbody.children[i].children[oldCol].style.backgroundColor = this.unselectedColor;
          }
          this.state.timeSelectedMin = parseInt(target.dataset.time);
          this.state.timeSelectedMax = parseInt(target.dataset.time);
          this.state.timeSelectedCol = parseInt(target.dataset.col);
          target.style.backgroundColor = this.selectedColor;

        }
      }
      else if(!this.state.isTimeSelected){
        this.state.timeSelectedMin = parseInt(target.dataset.time);
        this.state.timeSelectedMax = parseInt(target.dataset.time);
        this.state.isTimeSelected = true;
        this.state.timeSelectedCol = parseInt(target.dataset.col);
        target.style.backgroundColor = this.selectedColor;
      }
    }else if(target.style.backgroundColor == this.selectedColor){
      if(timeSelect == this.state.timeSelectedMin){
        while(target.dataset.time <= this.state.timeSelectedMax){
          target.style.backgroundColor = this.unselectedColor;
          target = target.parentNode.nextSibling.children[col];
        }
        this.state.isTimeSelected = false;
        this.state.timeSelectedMax = '';
        this.state.timeSelectedMin = '';
      }else{  
        //  min < timeSelect <= max
        var oldTimeSelectedMax = this.state.timeSelectedMax;
        this.state.timeSelectedMax = timeSelect;
        while(target.dataset.time < oldTimeSelectedMax){
          target = target.parentNode.nextSibling.children[col];
          target.style.backgroundColor = this.unselectedColor;
        }
      }          
    }
  }

  handleDateSelect(e) {
    var selectedYear = this.state.selectedDate.year;
    var selectedMonth = this.state.selectedDate.month;
    var selectedDate = this.state.selectedDate.date;
    var selectedDay = this.state.selectedDate.day;
    if(selectedMonth == this.state.month){
      if(selectedYear == this.state.year){
        var siblinges = e.target.parentNode.childNodes;
        siblinges[selectedDay + selectedDate - 1].style.backgroundColor = '#fff';
        if(selectedDate == parseInt(e.target.innerText)){
          this.setState({selectedDate: {year: '\u00a0', month: '', date: '', day: ''}});
          return;
        }
      }
    }

    e.target.style.backgroundColor = 'red';
    this.state.selectedDate.year = this.state.year;
    this.state.selectedDate.month = this.state.month;
    this.state.selectedDate.date = parseInt(e.target.innerText);
    this.state.selectedDate.day = this.getMonthFirstDate(this.state.year, this.state.month);
    this.setState();
  }

  handleScroll(e){
    e.target.firstChild.firstChild.firstChild.style.top = e.target.scrollTop + 'px';
  }

  render() {
    return (
      <div className="col-xs-12 col-sm-12">
        <div className="row">
          <div>
            <div>
              <div className="col-xs-2 col-sm-2 text-center" onMouseDown={this.handleMonthDelta.bind(this, -1)}>prev</div>
              <div className="col-xs-8 col-sm-8 text-center">{this.state.year}{this.monthWord[this.state.month]}</div>
              <div className="col-xs-2 col-sm-2 text-center" onMouseDown={this.handleMonthDelta.bind(this, 1)}>next</div>
            </div>
            <div>{this.weekBar}</div>
            <div>
              {this.getBlankPanel(this.state.year, this.state.month)}
              {this.getDatePanel(this.state.year, this.state.month)}
            </div>
          </div>
          <div className="col-xs-12 col-sm-12">
            
           <div className="row">
              <div className="chenken-table-responsive" onScroll={this.handleScroll}>
               {this.showTimeSeat()}
             </div>
           </div>
          
          </div>
        </div>
      </div>
    );
  }
}


var textSeat = '{"minSeat": 2, "maxSeat": 10, "minTime": 9, "maxTime": 22, "seat": {"19700520": ["001111111", "110000111", "111111111", "111111111", "111111111", "111111111", "100111111", "111100111", "101111011", "111111101", "111111111", "111111111", "111111111", "111100111"]}}';

var data = JSON.parse(textSeat);
console.log(data);
ReactDOM.render(
  <Calendar storeData={data} />,
  document.getElementById('chenken-reservation')
);