import './App.css'
import 'devextreme/dist/css/dx.fluent.saas.light.css';
import {BrowserRouter as Router} from 'react-router-dom';
import MainLayout from "./layout/MainLayout.tsx";


const App = () => {


  return (
    <Router>
      <MainLayout></MainLayout>
    </Router>
  )
}

export default App
