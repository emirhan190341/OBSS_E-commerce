import { useEffect } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Footer from "./components/home/Footer";
import Header from "./components/home/Header";
import AdminPage from "./pages/AdminPage";
import FavoritesPage from "./pages/FavoritesPage";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";

import SignupPage from "./pages/SignupPage";
import { useAuthStore } from "./store/authStore";
import BlackListPage from "./pages/BlackListPage";
import SellersPage from "./pages/SellersPage";
import SellerPage from "./pages/SellerPage";
import ProductsPage from "./pages/ProductsPage";
import ProductPage from "./pages/ProductPage";
import CartPage from "./pages/CartPage";
import UsersPage from "./pages/UsersPage";

function App() {
  const { checkAuth, isAuthenticated, user } = useAuthStore();
  console.log("user", user);

  useEffect(() => {
    checkAuth();
  }, [checkAuth]);

  return (
    <div className="min-h-screen w-full flex flex-col">
      <Header />

      <main className="flex-grow mb-20">
        <Routes>
          <Route path="/" element={<HomePage />} />

          <Route
            path="/login"
            element={!isAuthenticated ? <LoginPage /> : <Navigate to={"/"} />}
          />

          <Route
            path="/signup"
            element={!isAuthenticated ? <SignupPage /> : <Navigate to={"/"} />}
          />

          <Route
            path="/profile"
            element={isAuthenticated ? <AdminPage /> : <Navigate to={"/"} />}
          />

          <Route path="/products" element={<ProductsPage />} />

          <Route
            path="/favorites"
            element={
              isAuthenticated ? <FavoritesPage /> : <Navigate to={"/"} />
            }
          />

          <Route
            path="/blacklist"
            element={
              isAuthenticated ? <BlackListPage /> : <Navigate to={"/"} />
            }
          />

          <Route
            path="/users"
            element={isAuthenticated ? <UsersPage /> : <Navigate to={"/"} />}
          />

          <Route path="/sellers" element={<SellersPage />} />

          <Route path={`/seller/:id`} element={<SellerPage />} />

          <Route path={`/product/:id`} element={<ProductPage />} />

          <Route
            path="/cart"
            element={isAuthenticated ? <CartPage /> : <Navigate to={"/"} />}
          />
        </Routes>
      </main>

      <Footer />
    </div>
  );
}

export default App;
