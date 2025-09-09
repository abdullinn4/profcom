import './App.sass'
import {BrowserRouter} from "react-router-dom";
import {AppRouter} from "./AppRouter.tsx";
import {Header} from "../widgets";
import {AuthProvider} from "./AuthContext.tsx";

function App() {

  return (
      <AuthProvider>
          <BrowserRouter>
              <Header/>
              <AppRouter/>
          </BrowserRouter>
      </AuthProvider>
  )
}

export default App
