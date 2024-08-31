import React, { useEffect } from "react";
import { useParams } from "react-router-dom"; // Assuming you use React Router
import { useProductStore } from "../store/productStore"; // Adjust the path as needed
import { useCartStore } from "../store/cartStore";
import { useAuthStore } from "../store/authStore";

interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  quantity: number;
  logo: string;
  sellerId: number;
  categories: string[];
}

const ProductPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const {
    product,
    getProductById,
    isLoading,
    error,
    dencrementProductQuantity,
  } = useProductStore();
  const { addToCart } = useCartStore();
  const { user } = useAuthStore();
  const [quantity, setQuantity] = React.useState(1);

  useEffect(() => {
    if (id) {
      getProductById(id);
    }
  }, [id, getProductById]);

  const handleAddToCart = () => {
    if (product) {
      addToCart(user.id, product.id, 1);
      dencrementProductQuantity(product);
    }
  };

  if (isLoading) return <p className="text-center text-gray-700">Loading...</p>;
  if (error) return <p className="text-center text-red-500">Error: {error}</p>;

  if (!product)
    return <p className="text-center text-gray-700">No product found.</p>;

  return (
    <div className="max-w-screen-lg mx-auto p-4 md:p-8">
      <div className="bg-white shadow-lg rounded-lg overflow-hidden border border-gray-200">
        <div className="flex flex-col items-center md:flex-row">
          <img
            src={`http://localhost:8080/files/${product.logo}`}
            alt={product.name}
            className="w-full md:w-1/3 ml-4  h-64 object-cover rounded-t-lg md:rounded-l-lg"
          />

          <div className="p-6 flex flex-col justify-between items-center w-full ">
            <h1 className="text-3xl font-bold text-gray-900 mb-4">
              {product.name}
            </h1>
            <p className="text-gray-700 mb-4">{product.description}</p>
            <div className="mb-4 flex items-center">
              <span className="text-2xl font-semibold text-blue-600">
                ${product.price.toFixed(2)}
              </span>
              <span className="text-sm text-gray-600 ml-2">Price</span>
            </div>
            <div className="mb-4 flex items-center">
              <span className="text-lg font-semibold text-green-600">
                {product.quantity}
              </span>
              <span className="text-sm text-gray-600 ml-2">Available</span>
            </div>
            <div className="mb-4 flex flex-col items-center">
              <span className="text-lg font-semibold  text-gray-800">
                Categories:
              </span>
              <div className="flex flex-wrap mt-2">
                {product.categories.map((category, index) => (
                  <span
                    key={index}
                    className="text-sm text-white px-3 py-1 rounded-full m-1"
                    style={{
                      backgroundImage:
                        "linear-gradient(to right, #6a11cb, #2575fc)",
                    }}
                  >
                    {category}
                  </span>
                ))}
              </div>
            </div>
            <button
              onClick={handleAddToCart}
              className="mt-4 bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
            >
              Add to Cart
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
