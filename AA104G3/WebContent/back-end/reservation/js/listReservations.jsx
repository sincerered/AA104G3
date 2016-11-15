    class Excel extends React.Component {
      constructor(props) {
        super(props);
        this.rowData = this.rowData.bind(this);
        this.columnData = this.columnData.bind(this);
        this.header = this.header.bind(this);
        this._showEditor = this._showEditor.bind(this);
        this._save = this._save.bind(this);
        this.objectToArray = this.objectToArray.bind(this);
        this._showColumnEditor = this._showColumnEditor.bind(this);
        this._commit = this._commit.bind(this);
        this.componentDidUpdate = this.componentDidUpdate.bind(this);
        this._restoreAll = this._restoreAll.bind(this);
        this._restore = this._restore.bind(this);
        this.timepicker = this.timepicker.bind(this);
        this.state = {
          data: props.initialData,
          sortby: null,
          descending: false,
          edit: null,
          rowTemp: null,
          keys: Object.keys(props.heads),
          updateData: {},
          updatedData: {},
          updateColorClass: 'info',
        };       


        window.addEventListener('keydown', function(e) {
          if (e.key === 'Escape' && this.state.edit) {
            this.setState({
              updateData: {},
              edit: null,
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
              edit: null,
            });
          }


        }.bind(this), false);

      }
      
      componentDidUpdate() {
      	var state = this.state;
      	if (state.updateColorClass !== 'info') {
      		state.updateColorClass = 'info';
      		state.updateData = {};
      		state.updatedData = {};
      	}
      	jQuery('input[type="date"]').datepicker({
      		dateFormat: 'yy-mm-dd',
      		
      		onClose: function(e) {
      		  console.log('onclose');
      	      jQuery(this).trigger('focus'); 
      		},
      	});
      }
      
      
      objectToArray(obj, keys) {
        var arr = [];
        var len = keys.length;
        for (var i = 0; i < len; i++) arr.push(obj[keys[i]])
        return arr;
      }

      header(head, index) {
        return (
          <th onDoubleClick={this._showEditor}>{head}</th>
        );
      }
 
      _showEditor(e) {
        if (this.props.immutable[e.target.cellIndex] == 1) return;
        if (typeof e.target.dataset.row === 'undefined') {
          var edit = {
            row: 'all',
            cell: e.target.cellIndex,
          };
        } else if (e.target.dataset.type == 'button') {
          var edit = {
            row: parseInt(e.target.dataset.row, 10),
            cell: 'all',
          }
        } else {
          var edit = {
            row: parseInt(e.target.dataset.row, 10),
            cell: e.target.cellIndex,
          };
        }
        this.setState({edit: edit});
      }
      _showColumnEditor(e) {
        this.setState({edit: {
          row: 'all',
          cell: e.target.cellIndex,
        }});
      }
      _commit(e) {
      	var url = this.props.url;
      	var updatedData = this.state.updatedData;
      	var initialData = this.state.data;
      	var data = [];
      	for (var i in updatedData) {
      		var temp = initialData[parseInt(i)];
      		var tempUpdatedData = updatedData[i]
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
          data: {'data': JSON.stringify(data), 'action': 'updates'},
          success: function(resData) {
        	if (resData === 'commit') {
        		
        		var keys = Object.keys(updatedData);
        		
        		keys.forEach((key, index) => {initialData[key] = data[index]});
        		self.setState({
        			updateColorClass: 'success',
        			
        			
        		});
        	} else {
        		console.log(resData);
        		console.log(self.state);
        		self.setState({updateColorClass: 'danger'});
        	}
          },
        });
      }
      _save(e) {
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

  
      
      _restoreAll() {
    	  this.setState({
    		  updateData: {},
    		  updatedData: {},
    		  edit: null,
    	  });
      }
       
      _restore(e) {
    	  var updatedData = this.state.updatedData;
    	  delete updatedData[e.target.dataset.row];
    	  this.setState({
    		  updateData: {},
    		  updatedData: updatedData,
    		  edit: null,
    	  });
      }
      
      rowData(data, rowIndex) {
        this.state.rowTemp = rowIndex;
        
        return (
        <tr key={rowIndex}>
          {this.objectToArray(data, this.state.keys).map(this.columnData)}
          <td>
            <span onClick={this._showEditor} data-row={rowIndex} data-type={'button'}>
            	修改
            </span>
          </td>
          <td>
          <span onClick={this._restore} data-row={rowIndex}>
          	還原
          </span>
          </td>
        </tr>);
      }
      
      
      
      columnData(data, columnIndex) {
        var content = data;
        var edit = this.state.edit;
        var rowTemp = this.state.rowTemp;
        var key = this.state.keys[columnIndex];
        var newData = this.state.updatedData[rowTemp];
        if(edit && (edit.row === this.state.rowTemp || edit.row === 'all') && (edit.cell === columnIndex || edit.cell === 'all') && this.props.immutable[columnIndex] == 0){
          if (typeof newData !== 'undefined') {
            var value = newData[key];
            if (typeof value !== 'undefined') {
              content = value;
            }
          }
          
          if (key.search(/[Dd]ate$/) != -1) {
        	  console.log('date');
        	  return (
        	            <td>
        	            <input data-row={rowTemp} type="date" onFocus={this._save} defaultValue={content} data-key={key}/>
        	            </td>
        	          );  
          } else if (false) {
        	return (
        		<td>
        			<input data-row={rowTemp} type="hidden" onChange={this._save} defaultValue={content} data-key={key}/>
        			
        			
        		</td>
        	);  
          } else {
        	  console.log('text');
        	  return (
        	            <td>
        	            <input data-row={rowTemp} type="text" defaultValue={content} onBlur={this._save} data-key={key}/>
        	            </td>
        	          );
          }
          
        }
        if (typeof newData !== 'undefined') {
          var value = newData[key];
          if (typeof value != 'undefined') {
            return <td key={columnIndex} className={this.state.updateColorClass} data-row={rowTemp} onDoubleClick={this._showEditor}>{value}</td>
          }
        }

        return (<td key={columnIndex} data-row={rowTemp} onDoubleClick={this._showEditor}>{content}</td>);
      }
      
      timepicker(content, row, key) {
    	  
      }
      
      render() {
    	 
        return (
          <table className={'table'}>
            <thead>
              <tr>
                {this.objectToArray(this.props.heads, this.state.keys).map(this.header)}
                <th onClick={this._commit}>送出修改</th>
                <th onClick={this._restoreAll}>全部還原</th>
              </tr>

            </thead>
            <tbody>
              {this.state.data.map(this.rowData)}
            </tbody>
          </table>
        );
      }
    }
