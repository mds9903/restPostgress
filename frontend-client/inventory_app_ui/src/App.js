import "bootstrap/dist/css/bootstrap.min.css";

import { Route, Routes } from "react-router-dom";

import HomePage from "./pages/HomePage";
import ItemsPage from "./pages/ItemsPage";
import LocationsPage from "./pages/LocationsPage";
import SuppliesPage from "./pages/SuppliesPage";
import DemandsPage from "./pages/DemandsPage";
import ThresholdsPage from "./pages/ThresholdsPage";
import AvailabilityPage from "./pages/AvailabilityPage";
import NotFoundPage from "./pages/NotFoundPage";
import MainNav from "./layout/MainNav";

function App() {
  return (
    <div>
      <MainNav />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/items" element={<ItemsPage />} />
        <Route path="/locations" element={<LocationsPage />} />
        <Route path="/supplies" element={<SuppliesPage />} />
        <Route path="/demands" element={<DemandsPage />} />
        <Route path="/thresholds" element={<ThresholdsPage />} />
        <Route path="/availability" element={<AvailabilityPage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Routes>
    </div>
  );
}

export default App;
