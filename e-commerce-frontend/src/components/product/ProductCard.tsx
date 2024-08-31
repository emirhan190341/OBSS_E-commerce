import React, { useEffect } from "react";
import { Heart } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useFavoriteListStore } from "../../store/favoriteListStore";
import { useAuthStore } from "../../store/authStore";
import { useUserStore } from "../../store/userStore";

interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  logo: string;
}

interface ProductCardProps {
  product: Product;
}

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const { addFavorite } = useFavoriteListStore();
  const { getUserById } = useUserStore();
  const { user, setUserFavoriteProducts } = useAuthStore();

  const navigate = useNavigate();

  const handleAddFavorite = () => {
    if (user) {
      addFavorite(user.id, product.id);
      setUserFavoriteProducts(product.id);
    } else {
      navigate("/login");
    }
  };

  console.log(user);

  return (
    <div className="bg-white shadow-lg rounded-lg overflow-hidden relative transition-transform transform hover:scale-105 m-2">
      <div className="flex justify-center items-center p-4">
        <img
          src={`http://localhost:8080/files/${product.logo}`}
          alt={product.name}
          className="w-32 h-32 object-cover mx-auto"
        />
      </div>

      <button
        onClick={handleAddFavorite}
        className="absolute top-2 right-2 p-2 bg-white rounded-full shadow-md hover:bg-gray-100"
      >
        {user?.listOfFavoriteProducts?.includes(product.id) ? (
          <Heart className="text-red-500 fill-red-500 " />
        ) : (
          <Heart className="text-gray-500" />
        )}
      </button>

      <div className="p-4">
        <h2 className="text-lg font-semibold text-gray-800">{product.name}</h2>
        <p className="text-sm text-gray-600 mt-2">{product.description}</p>
        <div className="flex justify-between items-center mt-4">
          <span className="text-lg font-bold text-gray-800">
            ${product.price}
          </span>
          <Link
            to={`/product/${product.id}`}
            className="text-sm bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
          >
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
