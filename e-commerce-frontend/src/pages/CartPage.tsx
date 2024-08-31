import React, { useEffect } from "react";
import { useCartStore } from "../store/cartStore"; // Ensure this path is correct
import { useAuthStore } from "../store/authStore"; // Ensure this path is correct
import RotatedText from "../components/decorators/RotatedText";

interface CartItem {
  id: string;
  productId: string;
  productName: string;
  quantity: number;
  price: number;
  imageUrl: string;
}

const CartPage: React.FC = () => {
  const { cart, getCartByUserId, isLoading, error, deleteCartItems } =
    useCartStore();
  const { user } = useAuthStore();

  useEffect(() => {
    if (user) {
      getCartByUserId(user.id);
    }
  }, [user, getCartByUserId]);

  const handleOrderClick = () => {
    if (user) {
      deleteCartItems(user.id);
    }
  };

  return (
    <div className="max-w-screen-lg mx-auto p-4 md:p-8">
      <h1 className="text-3xl font-bold text-gray-900 text-center mb-6">
        Your <RotatedText>Cart</RotatedText>
      </h1>

      {isLoading && <p className="text-gray-500">Loading...</p>}

      {cart && cart.length === 0 && (
        <p className="text-gray-500">Your cart is empty.</p>
      )}

      <div className="bg-white shadow-md rounded-lg overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Product
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Quantity
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Price
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Total
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {cart &&
              cart.map((item: CartItem) => (
                <tr key={item.id}>
                  <td className="px-6 py-4 whitespace-nowrap flex items-center">
                    <img
                      src={`http://localhost:8080/files/${item.imageUrl}`}
                      alt={item.productName}
                      className="w-16 h-16 object-cover rounded-2xl mt-2"
                    />
                    <div className="text-sm font-medium text-gray-900">
                      {item.productName}
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-500">{item.quantity}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-500">
                      ${item.price.toFixed(2)}
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm font-medium text-gray-900">
                      ${(item.price * item.quantity).toFixed(2)}
                    </div>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>

        {cart && cart.length > 0 && (
          <div className="p-4 flex justify-end bg-gray-50">
            <div className="text-lg font-bold text-gray-900">
              Total: $
              {cart
                .reduce((acc, item) => acc + item.price * item.quantity, 0)
                .toFixed(2)}
            </div>
          </div>
        )}
      </div>

      <div className="flex justify-center mt-4">
        <button
          onClick={handleOrderClick}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
        >
          ORDER
        </button>
      </div>
    </div>
  );
};

export default CartPage;
