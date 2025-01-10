import {Button, DataGrid, NumberBox} from "devextreme-react";
import {Column, DataGridRef, Editing, Selection} from "devextreme-react/data-grid";
import {useCallback, useRef, useState} from "react";
import {CustomStore} from "devextreme/common/data";
import axios from "axios";
import {SelectionChangedEvent} from "devextreme/ui/data_grid";
import {ValueChangedEvent} from "devextreme/ui/number_box";

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

interface Product {
  id: number | undefined;
  name: string;
  type: string;
  quantity: number;
  price: number;
}

const initProduct = {
  id: undefined,
  name: '',
  type: '',
  quantity: 1,
  price: 0,
}


export default function ProductList() {
  const dataGridRef = useRef<DataGridRef>(null);
  const [selectProduct, setSelectProduct] = useState<Product>(initProduct);

  const onSelectionChanged = useCallback(({selectedRowsData}: SelectionChangedEvent) => {
    const data = selectedRowsData[0];
    data.quantity = 1;
    setSelectProduct(data);
  }, []);

  const PayCell = () => {
    return (<Button
      width={200}
      text="카카오페이로 결제하기"
      type="normal"
      stylingMode="contained"
      onClick={onClick}
    />)
  }


  const quantityChanged = (e: ValueChangedEvent) => {
    setSelectProduct({...selectProduct, quantity: e.value});
  }

  const onClick = async () => {
    await axios.get('/api/orders/ready', {params: {productId: selectProduct.id, quantity: selectProduct.quantity}}).then((response) => {
      location.href = response.data.next_redirect_pc_url;
    });
  }

  return (
    <div>
      <DataGrid
        ref={dataGridRef}
        dataSource={store}
        showBorders={true}
        onSelectionChanged={onSelectionChanged}
        remoteOperations={true}
        width={'50%'}
      >
        <Selection mode="single"/>
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
        {/*<Column caption="Payment" cellRender={PayCell} visible={true}/>*/}
      </DataGrid>
      {selectProduct && (
        <div id="employee-info">
          {selectProduct.id && (
            <div>
              <div>{selectProduct.name}</div>
              <div>{selectProduct.price}</div>
              <NumberBox
                value={selectProduct.quantity}
                min={1}
                max={20}
                showSpinButtons={true}
                onValueChanged={quantityChanged}
              />
              <PayCell/>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
