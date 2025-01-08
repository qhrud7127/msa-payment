import './App.css'
import 'devextreme/dist/css/dx.fluent.saas.light.css';
import {DataGrid} from "devextreme-react";
import {CustomStore} from "devextreme/common/data";
import {Column, Editing} from "devextreme-react/data-grid";
import axios from "axios";

const store = new CustomStore({
  key: 'id',
  async load(loadOptions) {
    try {
      const response = await axios.get('/api/products');
      const result = await response.data;
      console.log(result);
      return {
        data: result,
        totalCount: 0
      };
    } catch (err) {
      throw new Error('Data Loading Error');
    }
  },
  insert: (values) => {
    console.log(values);
    return axios.post('/api/products', values)
  },
});

const App = () => {
  return (
    <>
      <DataGrid
        dataSource={store}
        showBorders={true}
        remoteOperations={true}
      >
        <Editing
          mode="row"
          allowUpdating={true}
          allowDeleting={true}
          allowAdding={true}/>
        <Column
          dataField="type"
          dataType="string"
        />
        <Column
          dataField="name"
          dataType="string"
        />
      </DataGrid>
    </>
  )
}

export default App
