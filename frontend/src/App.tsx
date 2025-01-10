import './App.css'
import 'devextreme/dist/css/dx.fluent.saas.light.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import ProductList from "./components/product/list/ProductList.tsx";
import Approve from "./components/kakaopay/approve/Approve.tsx";


const App = () => {


  return (
    <>
      <Router>
        <Routes>
          <Route path="*" element={<ProductList/>}/>
          <Route path="/approve/pc/popup" element={<Approve/>}/>
        </Routes>
      </Router>
    </>
  )
}

export default App
