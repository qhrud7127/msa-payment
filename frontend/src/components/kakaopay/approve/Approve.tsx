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
    <>
      {!result &&
        <Button onClick={handleApprove}>
          버튼을 누르면 결제가 완료됩니다.
        </Button>
      }
      {result &&
        <div>
          <p>{result?.amount.total}원이 결제 되었습니다.</p>
          <a href={'/'}>
            상품 목록으로 돌아가기
          </a>
        </div>
      }
    </>
  );
}
