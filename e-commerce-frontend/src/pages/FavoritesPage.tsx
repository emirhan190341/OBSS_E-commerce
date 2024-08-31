import { ArrowRight, X } from "lucide-react";
import { useEffect } from "react";
import { useAuthStore } from "../store/authStore";
import { useFavoriteListStore } from "../store/favoriteListStore";
import { Link } from "react-router-dom";

const FavoritesPage = () => {
  const { products, getFavoriteProducts, deleteFavorite, isLoading, error } =
    useFavoriteListStore();
  const { user, setUserFavoriteProducts } = useAuthStore();
  const userId = user.id;

  useEffect(() => {
    getFavoriteProducts(userId);
  }, [getFavoriteProducts, userId, deleteFavorite]);

  const handleDeleteFavorite = (productId: string) => {
    deleteFavorite(userId, productId);
    setUserFavoriteProducts(productId);
  };

  if (isLoading) return <p className="text-center text-gray-700">Loading...</p>;

  return (
    <div className="max-w-screen-xl mx-auto p-4 md:p-8">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-900">
        Favorite Products
      </h1>

      {products.length === 0 ? (
        <p className="text-center text-gray-600">No favorite products.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {products.map((product) => (
            <div
              key={product.id}
              className="bg-white rounded-lg shadow-md overflow-hidden transition-transform transform hover:scale-105"
            >
              <button
                onClick={() => handleDeleteFavorite(product.id)}
                className="absolute right-0 hover:bg-red-500"
              >
                <X />
              </button>

              <div className="flex justify-center items-center p-2">
                <img
                  src={`http://localhost:8080/files/${product.logo}`}
                  alt={product.name}
                  className="w-24 h-24 object-cover"
                />
              </div>

              <div className="p-2">
                <div className="flex items-center justify-between">
                  <h2 className="text-sm font-semibold text-gray-800 mb-1">
                    {product.name}
                  </h2>
                  <Link
                    to={`/product/${product.id}`}
                    className="text-sm flex items-center justify-center"
                  >
                    <ArrowRight />
                  </Link>
                </div>

                <p className="text-xs text-gray-600 mb-1">
                  {product.description}
                </p>
                <p className="text-sm font-bold text-gray-800">
                  ${product.price}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default FavoritesPage;
