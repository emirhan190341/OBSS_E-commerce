import { useEffect } from "react";
import { Link } from "react-router-dom";
import RotatedText from "../components/decorators/RotatedText";
import ProductCard from "../components/product/ProductCard";
import SellerCard from "../components/seller/SellerCard";
import { useProductStore } from "../store/productStore";
import { useSellerStore } from "../store/sellerStore";
import { useAuthStore } from "../store/authStore";

const HomePage = () => {
  const {
    products,
    searchProductsWithoutBlacklisted,
    addNewProduct,
    getProducts,
  } = useProductStore();
  const { user } = useAuthStore();

  const { sellers, getSellersSortedByDescRating } = useSellerStore();

  useEffect(() => {
    if (!user) {
      getProducts(0, 6);
    } else {
      searchProductsWithoutBlacklisted(user?.id, "", 0, 6);
    }

    getSellersSortedByDescRating(0, 6);
  }, [getSellersSortedByDescRating]);

  return (
    <div className="max-w-screen-xl mx-auto p-4 md:p-8">
      <div className="flex justify-between mb-8 text-xl font-bold border-b-2 border-b-gray-900 pb-2">
        <Link to="/products">Products</Link>

        {user?.isAdmin && <Link to="/users">Users</Link>}
        
        <Link to="/sellers">Sellers</Link>
      </div>

      <h1 className="text-3xl font-extrabold text-gray-900 text-center mb-8">
        Welcome to Our <RotatedText>E-commerce</RotatedText> Store
      </h1>

      <div className="border rounded-xl mb-10">
        <h2 className="text-2xl font-bold bg-gradient-to-r from-sky-500 to-slate-800 text-transparent bg-clip-text rounded-lg  mb-4 text-center">
          Products
        </h2>

        {products?.length === 0 ? (
          <p className="text-center text-gray-500">No products found.</p>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6  ">
            {products?.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        )}
      </div>

      <div className="border rounded-xl mb-10">
        <h2 className="text-2xl font-bold bg-gradient-to-r from-sky-500 to-slate-800 text-transparent bg-clip-text rounded-lg  mb-4 text-center">
          Our Best Sellers
        </h2>

        {sellers?.length === 0 ? (
          <p className="text-center text-gray-500">No sellers found.</p>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {sellers?.map((seller) => (
              <SellerCard key={seller.id} seller={seller} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
