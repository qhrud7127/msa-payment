import {Button, DataGrid} from "devextreme-react";
import {Column, DataGridRef, Editing} from "devextreme-react/data-grid";
import {useRef} from "react";
import {CustomStore} from "devextreme/common/data";
import axios from "axios";

const store = new CustomStore({
  key: 'id',
  async load() {
    try {
      const response = await axios.get('/api/products/list');
      const result = await response.data;
      return {
        data: result,
        totalCount: 0
      };
    } catch (err) {
      throw new Error('Data Loading Error: ' + err);
    }
  },
  insert: (values) => {
    return axios.post('/api/products/create', values)
  },
  update: (key, values) => {
    values.id = key;
    return axios.post('/api/products/update', values)
  },
  remove: (key) => {
    return axios.get('/api/products/delete', {params: {id: key}})
  },
});


const onClick = async (data) => {
  await axios.get('/api/orders/ready', {params: {productId: data.id}}).then((response) => {
    location.href = response.data.next_redirect_pc_url;
  });
}


export default function ProductList() {
  const dataGridRef = useRef<DataGridRef>(null);

  const PayCell = (e: any) => {
    return (<Button
      width={200}
      text="카카오페이로 결제하기"
      type="normal"
      stylingMode="contained"
      onClick={() => onClick(e.data)}
    />)
  }

  return (
    <div>
      <DataGrid
        ref={dataGridRef}
        dataSource={store}
        showBorders={true}
        remoteOperations={true}
      >
        <Editing
          mode="row"
          allowDeleting={true}
          allowUpdating={true}
          allowAdding={true}/>
        <Column
          dataField="name"
          dataType="string"
        />
        <Column
          dataField="type"
          dataType="string"
        />
        <Column
          dataField="price"
          dataType="number"
        />
        <Column caption="Payment" cellRender={PayCell} visible={true}/>
      </DataGrid>
    </div>
  );
}
