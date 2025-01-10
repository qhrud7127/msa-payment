import {Route, Routes} from "react-router-dom";
import ProductList from "../components/product/list/ProductList.tsx";
import Approve from "../components/kakaopay/approve/Approve.tsx";

export default function Content() {
  return (
    <div>
      <Routes>
        <Route path="*" element={<ProductList/>}/>
        <Route path="/approve/pc/popup" element={<Approve/>}/></Routes>
    </div>
  );
}
