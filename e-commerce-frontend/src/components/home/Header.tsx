import {
  BookHeart,
  BookX,
  Home,
  LogIn,
  LogOut,
  Search,
  ShoppingCart,
  User,
} from "lucide-react";
import { useState } from "react";
import { Toaster } from "react-hot-toast";
import { Link, useLocation } from "react-router-dom";
import { useAuthStore } from "../../store/authStore";
import { useProductStore } from "../../store/productStore";

const Header = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const { isAuthenticated, logout, user } = useAuthStore();
  const { searchProductsWithoutBlacklisted, searchProducts } =
    useProductStore();

  const location = useLocation();
  const currentPath = location.pathname;

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();

    if (!user) {
      searchProducts(searchQuery, 0, 6);
    } else {
      searchProductsWithoutBlacklisted(user.id, searchQuery);
    }
  };

  return (
    <div className="px-4">
      <div className="max-w-[1200px] sticky top-0 left-0 w-full md:mx-auto mt-8 shadow-xl">
        <div className="flex w-full items-center justify-between">
          <div className="flex justify-center gap-10 items-center">
            <img
              className="w-28 object-cover"
              src="/src/imgs/logo.jpg"
              alt="Logo"
            />

            {currentPath != "/profile" && (
              <form
                onSubmit={handleSearch}
                className="flex items-center bg-white rounded-lg shadow-sm overflow-hidden"
              >
                <input
                  type="text"
                  placeholder="Type the product name or category you are looking for."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="py-2 px-3 border-none outline-none text-sm md:text-base w-64 md:w-96 placeholder:text-sm" // Increased width
                />
                <button
                  type="submit"
                  className="flex items-center justify-center bg-gray-100 p-2"
                >
                  <Search className="text-gray-600" />
                </button>
              </form>
            )}
          </div>

          <div className="text-black text-sm md:text-base flex gap-5 mr-4">
            <Link to="/" className="flex items-center gap-1 py-2 rounded-lg">
              <Home /> Home
            </Link>

            {isAuthenticated && (
              <Link
                to="/favorites"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <BookHeart /> Favorites
              </Link>
            )}

            {isAuthenticated && (
              <Link
                to="/blacklist"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <BookX /> Blacklist
              </Link>
            )}

            {isAuthenticated && (
              <Link
                to="/profile"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <User /> Profile
              </Link>
            )}

            {isAuthenticated && (
              <Link
                to="/cart"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <ShoppingCart /> Cart
              </Link>
            )}

            {!isAuthenticated && (
              <Link
                to="/login"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <User /> Login
              </Link>
            )}

            {!isAuthenticated && (
              <Link
                to="/signup"
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <LogIn /> Signup
              </Link>
            )}

            {isAuthenticated && (
              <button
                onClick={logout}
                className="flex items-center gap-1 py-2 rounded-lg"
              >
                <LogOut /> Logout
              </button>
            )}
          </div>

          <Toaster
            toastOptions={{
              style: {
                border: "1px solid #33F3FF",
                padding: "3px",
                color: "black",
                background: "#f3f4f6",
                fontSize: "12px",
              },
            }}
            position="top-right"
            reverseOrder={false}
          />
        </div>
      </div>
    </div>
  );
};

export default Header;
