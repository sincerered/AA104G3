'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Stobh = function (_React$Component) {
  _inherits(Stobh, _React$Component);

  function Stobh(props) {
    _classCallCheck(this, Stobh);

    var _this = _possibleConstructorReturn(this, (Stobh.__proto__ || Object.getPrototypeOf(Stobh)).call(this, props));

    _this.selectedColor = 'rgb(200, 50, 200)';
    _this.unselectedColor = 'rgb(255, 255, 255)';
    _this.unavailableColor = 'rgb(100, 100, 100)';

    _this.weeks = ["日", "一", "二", "三", "四", "五", "六"];

    _this.showStobhTable = _this.showStobhTable.bind(_this);
    _this.openTime = _this.openTime.bind(_this);
    _this.openTimeRow = _this.openTimeRow.bind(_this);
    _this.handleMouseEnter = _this.handleMouseEnter.bind(_this);
    _this.stobhToString = _this.stobhToString.bind(_this);

    if (typeof _this.props.stobh === 'undefined' || _this.props.stobh.length < 168) {
      var stobh = [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]];
    } else {
      var stobh = [];
      var stobh_string = _this.props.stobh;
      for (var i = 0; i < 7; i++) {
        var arr = [];
        for (var j = 0; j < 24; j++) {
          arr[j] = stobh_string[i * 24 + j];
        }
        stobh[i] = arr;
      }
    }

    _this.state = {
      stobh: stobh,
      stobh_string: '',
      isMouseDown: false,
      color: [_this.props.unselectedColor, _this.props.selectedColor]
    };

    window.addEventListener('mousedown', function (e) {
      this.state.isMouseDown = true;
      var target = e.target;
      var row = target.dataset.row;
      var week = target.dataset.week;
      if (this.state.stobh[week][row] == 0) {
        this.state.stobh[week][row] = 1;
        target.style.backgroundColor = this.state.color[1];
      } else {
        this.state.stobh[week][row] = 0;
        target.style.backgroundColor = this.state.color[0];
      }
    }.bind(_this), false);

    window.addEventListener('mouseup', function (e) {
      this.state.isMouseDown = false;
      console.log('mouseUp');
      this.state.stobh_string = this.stobhToString(this.state.stobh);
      this.setState();
    }.bind(_this), false);

    return _this;
  }

  _createClass(Stobh, [{
    key: 'handleMouseEnter',
    value: function handleMouseEnter(e) {
      if (this.state.isMouseDown) {
        var target = e.target;
        var row = target.dataset.row;
        var week = target.dataset.week;
        if (this.state.stobh[week][row] == 0) {
          this.state.stobh[week][row] = 1;
          target.style.backgroundColor = this.state.color[1];
        } else {
          this.state.stobh[week][row] = 0;
          target.style.backgroundColor = this.state.color[0];
        }
      }
    }
  }, {
    key: 'stobhToString',
    value: function stobhToString(arr) {
      var str = '';
      for (var i = 0; i < 7; i++) {
        str += arr[i].join('');
      }
      return str;
    }
  }, {
    key: 'showStobhTable',
    value: function showStobhTable() {
      return React.createElement(
        'table',
        { className: 'table text-center' },
        React.createElement(
          'thead',
          null,
          React.createElement(
            'tr',
            null,
            this.weeks.map(function (a) {
              return React.createElement(
                'th',
                { className: 'text-center' },
                a
              );
            })
          )
        ),
        React.createElement(
          'tbody',
          null,
          this.openTime()
        )
      );
    }
  }, {
    key: 'openTime',
    value: function openTime() {
      var arr = [];
      for (var i = 0; i < 24; i++) {
        arr[i] = React.createElement(
          'tr',
          null,
          this.openTimeRow(i)
        );
      }
      return arr;
    }
  }, {
    key: 'openTimeRow',
    value: function openTimeRow(row) {
      var arr = [];
      for (var i = 0; i < 7; i++) {
        arr[i] = React.createElement(
          'td',
          { style: { backgroundColor: this.state.color[this.state.stobh[i][row]] }, 'data-row': row, 'data-week': i, onMouseEnter: this.handleMouseEnter },
          row + ':00 - ' + row + ':59'
        );
      }
      return arr;
    }
  }, {
    key: 'render',
    value: function render() {
      return React.createElement(
        'div',
        { className: '' },
        this.showStobhTable(),
        React.createElement('input', { type: 'hidden', id: 'stobh_string', value: this.state.stobh_string, name: 'stobh' })
      );
    }
  }]);

  return Stobh;
}(React.Component);