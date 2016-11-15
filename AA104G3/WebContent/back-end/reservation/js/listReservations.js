'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Excel = function (_React$Component) {
  _inherits(Excel, _React$Component);

  function Excel(props) {
    _classCallCheck(this, Excel);

    var _this = _possibleConstructorReturn(this, (Excel.__proto__ || Object.getPrototypeOf(Excel)).call(this, props));

    _this.rowData = _this.rowData.bind(_this);
    _this.columnData = _this.columnData.bind(_this);
    _this.header = _this.header.bind(_this);
    _this._showEditor = _this._showEditor.bind(_this);
    _this._save = _this._save.bind(_this);
    _this.objectToArray = _this.objectToArray.bind(_this);
    _this._showColumnEditor = _this._showColumnEditor.bind(_this);
    _this._commit = _this._commit.bind(_this);
    _this.componentDidUpdate = _this.componentDidUpdate.bind(_this);
    _this._restoreAll = _this._restoreAll.bind(_this);
    _this._restore = _this._restore.bind(_this);
    _this.timepicker = _this.timepicker.bind(_this);
    _this.state = {
      data: props.initialData,
      sortby: null,
      descending: false,
      edit: null,
      rowTemp: null,
      keys: Object.keys(props.heads),
      updateData: {},
      updatedData: {},
      updateColorClass: 'info'
    };

    window.addEventListener('keydown', function (e) {
      if (e.key === 'Escape' && this.state.edit) {
        this.setState({
          updateData: {},
          edit: null
        });
      } else if (e.key === 'Enter' && this.state.edit != null) {
        e.target.blur();
        var updateData = this.state.updateData;
        var updatedData = this.state.updatedData;
        for (var i in updateData) {
          if (typeof updatedData[i] == 'undefined') {
            updatedData[i] = {};
          }
          for (var j in updateData[i]) {
            updatedData[i][j] = updateData[i][j];
          }
        }

        this.setState({
          updateData: {},
          updatedData: updatedData,
          edit: null
        });
      }
    }.bind(_this), false);

    return _this;
  }

  _createClass(Excel, [{
    key: 'componentDidUpdate',
    value: function componentDidUpdate() {
      var state = this.state;
      if (state.updateColorClass !== 'info') {
        state.updateColorClass = 'info';
        state.updateData = {};
        state.updatedData = {};
      }
      jQuery('input[type="date"]').datepicker({
        dateFormat: 'yy-mm-dd',

        onClose: function onClose(e) {
          console.log('onclose');
          jQuery(this).trigger('focus');
        }
      });
    }
  }, {
    key: 'objectToArray',
    value: function objectToArray(obj, keys) {
      var arr = [];
      var len = keys.length;
      for (var i = 0; i < len; i++) {
        arr.push(obj[keys[i]]);
      }return arr;
    }
  }, {
    key: 'header',
    value: function header(head, index) {
      return React.createElement(
        'th',
        { onDoubleClick: this._showEditor },
        head
      );
    }
  }, {
    key: '_showEditor',
    value: function _showEditor(e) {
      if (this.props.immutable[e.target.cellIndex] == 1) return;
      if (typeof e.target.dataset.row === 'undefined') {
        var edit = {
          row: 'all',
          cell: e.target.cellIndex
        };
      } else if (e.target.dataset.type == 'button') {
        var edit = {
          row: parseInt(e.target.dataset.row, 10),
          cell: 'all'
        };
      } else {
        var edit = {
          row: parseInt(e.target.dataset.row, 10),
          cell: e.target.cellIndex
        };
      }
      this.setState({ edit: edit });
    }
  }, {
    key: '_showColumnEditor',
    value: function _showColumnEditor(e) {
      this.setState({ edit: {
          row: 'all',
          cell: e.target.cellIndex
        } });
    }
  }, {
    key: '_commit',
    value: function _commit(e) {
      var url = this.props.url;
      var updatedData = this.state.updatedData;
      var initialData = this.state.data;
      var data = [];
      for (var i in updatedData) {
        var temp = initialData[parseInt(i)];
        var tempUpdatedData = updatedData[i];
        for (var j in tempUpdatedData) {
          temp[j] = tempUpdatedData[j];
        }
        data.push(temp);
      }
      var self = this;
      $.ajax({
        type: 'get',
        url: url,
        dataType: 'text',
        data: { 'data': JSON.stringify(data), 'action': 'updates' },
        success: function success(resData) {
          if (resData === 'commit') {

            var keys = Object.keys(updatedData);

            keys.forEach(function (key, index) {
              initialData[key] = data[index];
            });
            self.setState({
              updateColorClass: 'success'

            });
          } else {
            console.log(resData);
            console.log(self.state);
            self.setState({ updateColorClass: 'danger' });
          }
        }
      });
    }
  }, {
    key: '_save',
    value: function _save(e) {
      console.log('save');
      var input = e.target;
      var newValue = input.value;
      var row = input.dataset.row;
      if (input.defaultValue != newValue) {
        if (typeof this.state.updateData[row] === 'undefined') {
          var newData = {};
          newData[input.dataset.key] = newValue;
          this.state.updateData[row] = newData;
        } else {
          this.state.updateData[row][input.dataset.key] = newValue;
        }
      }
    }
  }, {
    key: '_restoreAll',
    value: function _restoreAll() {
      this.setState({
        updateData: {},
        updatedData: {},
        edit: null
      });
    }
  }, {
    key: '_restore',
    value: function _restore(e) {
      var updatedData = this.state.updatedData;
      delete updatedData[e.target.dataset.row];
      this.setState({
        updateData: {},
        updatedData: updatedData,
        edit: null
      });
    }
  }, {
    key: 'rowData',
    value: function rowData(data, rowIndex) {
      this.state.rowTemp = rowIndex;

      return React.createElement(
        'tr',
        { key: rowIndex },
        this.objectToArray(data, this.state.keys).map(this.columnData),
        React.createElement(
          'td',
          null,
          React.createElement(
            'span',
            { onClick: this._showEditor, 'data-row': rowIndex, 'data-type': 'button' },
            '\u4FEE\u6539'
          )
        ),
        React.createElement(
          'td',
          null,
          React.createElement(
            'span',
            { onClick: this._restore, 'data-row': rowIndex },
            '\u9084\u539F'
          )
        )
      );
    }
  }, {
    key: 'columnData',
    value: function columnData(data, columnIndex) {
      var content = data;
      var edit = this.state.edit;
      var rowTemp = this.state.rowTemp;
      var key = this.state.keys[columnIndex];
      var newData = this.state.updatedData[rowTemp];
      if (edit && (edit.row === this.state.rowTemp || edit.row === 'all') && (edit.cell === columnIndex || edit.cell === 'all') && this.props.immutable[columnIndex] == 0) {
        if (typeof newData !== 'undefined') {
          var value = newData[key];
          if (typeof value !== 'undefined') {
            content = value;
          }
        }

        if (key.search(/[Dd]ate$/) != -1) {
          console.log('date');
          return React.createElement(
            'td',
            null,
            React.createElement('input', { 'data-row': rowTemp, type: 'date', onFocus: this._save, defaultValue: content, 'data-key': key })
          );
        } else if (false) {
          return React.createElement(
            'td',
            null,
            React.createElement('input', { 'data-row': rowTemp, type: 'hidden', onChange: this._save, defaultValue: content, 'data-key': key })
          );
        } else {
          console.log('text');
          return React.createElement(
            'td',
            null,
            React.createElement('input', { 'data-row': rowTemp, type: 'text', defaultValue: content, onBlur: this._save, 'data-key': key })
          );
        }
      }
      if (typeof newData !== 'undefined') {
        var value = newData[key];
        if (typeof value != 'undefined') {
          return React.createElement(
            'td',
            { key: columnIndex, className: this.state.updateColorClass, 'data-row': rowTemp, onDoubleClick: this._showEditor },
            value
          );
        }
      }

      return React.createElement(
        'td',
        { key: columnIndex, 'data-row': rowTemp, onDoubleClick: this._showEditor },
        content
      );
    }
  }, {
    key: 'timepicker',
    value: function timepicker(content, row, key) {}
  }, {
    key: 'render',
    value: function render() {

      return React.createElement(
        'table',
        { className: 'table' },
        React.createElement(
          'thead',
          null,
          React.createElement(
            'tr',
            null,
            this.objectToArray(this.props.heads, this.state.keys).map(this.header),
            React.createElement(
              'th',
              { onClick: this._commit },
              '\u9001\u51FA\u4FEE\u6539'
            ),
            React.createElement(
              'th',
              { onClick: this._restoreAll },
              '\u5168\u90E8\u9084\u539F'
            )
          )
        ),
        React.createElement(
          'tbody',
          null,
          this.state.data.map(this.rowData)
        )
      );
    }
  }]);

  return Excel;
}(React.Component);