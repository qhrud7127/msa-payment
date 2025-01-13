import Header from "./Header.tsx";
import Explorer from "./Explorer.tsx";
import Footer from "./Footer.tsx";
import Content from "./Content.tsx";

export default function MainLayout() {
  return (
    <div id={'main-layout'}>
      <Header/>
      <Explorer/>
      <Content/>
      <Footer/>
    </div>
  );
}
