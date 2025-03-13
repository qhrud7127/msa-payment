import {Button} from "devextreme-react";
import axios from "axios";
import {useLocation} from "react-router-dom";
import {useState} from "react";

export default function Approve() {
  const location = useLocation();
  const url = location.search;
  const pgToken = url.split('=')[1];

  const [result, setResult] = useState(null);

  const handleApprove = async () => {
    await axios.get('/api/orders/approve', {params: {pg_token: pgToken}}).then((response) => {
      setResult(response.data);
      window.close();//결제 완료후 창이 닫긴다.
    }).catch((error) => {
      console.error(error);
    })
  };

  return (
    <div className={'flex flex-col items-center'}>
      {!result &&
        <Button className={'my-10'} onClick={handleApprove}>
          이 버튼을 누르면 결제가 완료됩니다.
          해당 버튼을 눌러 주세요.
        </Button>
      }
      {result &&
        <div className={'flex flex-col items-center'}>
          <div className={'font-bold text-xl my-10'}>{result?.amount.total.toLocaleString('ko-KR')}원이 결제 되었습니다.</div>
          <a className={'px-4 py-2 border-1 rounded-xl bg-blue-100 hover:bg-blue-200'} href={'/'}>
            상품 목록으로 돌아가기
          </a>
        </div>
      }
    </div>
  );
}
