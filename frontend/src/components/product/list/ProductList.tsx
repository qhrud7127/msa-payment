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

  const PayButton = () => {
    return (<Button
      width={200}
      text="카카오페이로 결제하기"
      type="default"
      stylingMode="contained"
      onClick={onClick}
    />)
  }


  const quantityChanged = (e: ValueChangedEvent) => {
    setSelectProduct({...selectProduct, quantity: e.value});
  }

  const onClick = async () => {
    await axios.get('/api/orders/ready', {
      params: {
        productId: selectProduct.id,
        quantity: selectProduct.quantity
      }
    }).then((response) => {
      location.href = response.data.next_redirect_pc_url;
    });
  }

  return (
    <div className={''}>
      <DataGrid
        ref={dataGridRef}
        dataSource={store}
        showBorders={true}
        onSelectionChanged={onSelectionChanged}
        remoteOperations={true}
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
      </DataGrid>
      {selectProduct.id && (
        <div className={'flex justify-between m-4 '}>
          <div className={"flex items-center w-full"}>
            <div className={'w-2/3'}>
              {selectProduct.name}
            </div>
            <div className={''}>
              <div className={"mb-2"}>
                {selectProduct.price.toLocaleString('ko-KR')}원
              </div>
              <NumberBox
                className=" pb-2"
                value={selectProduct.quantity}
                min={1}
                max={20}
                showSpinButtons={true}
                onValueChanged={quantityChanged}
              />
            </div>
          </div>
          <div className={"rounded-2xl bg-zinc-100 p-10"}>
            <div className={"flex justify-between"}>
              <div className={'font-bold pb-1'}>결제 예정 금액</div>
              <div className={"mb-4 pb-2"}>
                {(selectProduct.price * selectProduct.quantity).toLocaleString('ko-KR')}원
              </div>
            </div>
            <PayButton/>
          </div>
        </div>
      )}
    </div>
  );
}
