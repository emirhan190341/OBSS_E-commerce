import { ArrowRight } from "lucide-react";
import { useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import ProductForm from "../components/product/ProductForm";
import { useAuthStore } from "../store/authStore";
import { useProductStore } from "../store/productStore";
import { useSellerStore } from "../store/sellerStore";

const SellerPage = () => {
  const { seller, getSellerById, isLoading } = useSellerStore();
  const { getProductsBySellerId, products } = useProductStore();
  const { user } = useAuthStore();
  
  console.log(user);
  const { id } = useParams();

  useEffect(() => {
    getSellerById(id);
    getProductsBySellerId(id);
  }, [getSellerById, getProductsBySellerId, id]);

  if (isLoading) return <p className="text-center text-gray-700">Loading...</p>;

  if (!seller) return null;

  return (
    <div className="max-w-screen-md mx-auto p-4 md:p-8 bg-white shadow-lg rounded-lg m-10">
      <div className="flex justify-center items-center mb-6">
        <img
          src={`http://localhost:8080/files/${seller.logo}`}
          alt={seller.companyName}
          className="w-32 h-32 object-cover rounded-full shadow-md"
        />
      </div>
      <h1 className="text-3xl font-bold text-gray-900 text-center mb-4">
        {seller.companyName}
      </h1>
      <p className="text-gray-600 text-center mb-4">
        Rating:{" "}
        <span className="text-yellow-500 font-semibold">{seller.rating}</span>
      </p>
      <div className="text-center mb-6">
        <p className="text-gray-700 mb-2">
          <span className="font-semibold">Address:</span> {seller.address}
        </p>
        <p className="text-gray-700 mb-2">
          <span className="font-semibold">Contact Number:</span>{" "}
          {seller.contactNumber}
        </p>
        <p className="text-gray-700 mb-2">
          <span className="font-semibold">Email:</span>{" "}
          <a
            href={`mailto:${seller.email}`}
            className="text-blue-500 hover:underline"
          >
            {seller.email}
          </a>
        </p>
        <p className="text-gray-700 mb-2">
          <span className="font-semibold">Website:</span>{" "}
          <a
            href={seller.websiteUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500 hover:underline"
          >
            {seller.websiteUrl}
          </a>
        </p>
        {seller.isVerified && (
          <p className="text-green-600 font-semibold mt-4">Verified Seller</p>
        )}
      </div>

      <h2 className="text-xl font-semibold text-gray-900 mb-4">Products</h2>

      {user?.isAdmin && (
        <button
          className="btn"
          onClick={() => document.getElementById("my_modal_1").showModal()}
        >
          ADD NEW PRODUCT
        </button>
      )}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {products.map((product) => (
          <div key={product.id} className="bg-white shadow-lg rounded-lg p-4 ">
            <div className="flex justify-between items-center">
              <h3 className="text-lg font-semibold text-gray-900 mt-2">
                {product.name}
              </h3>
              <Link to={`/product/${product.id}`}>
                <ArrowRight size={24} className="text-gray-700" />
              </Link>
            </div>
            <p
              className="text-gray-700 mt-2 overflow-hidden text-ellipsis"
              style={{
                display: "-webkit-box",
                WebkitLineClamp: 3,
                WebkitBoxOrient: "vertical",
                whiteSpace: "normal",
              }}
            >
              {product.description}
            </p>
            <p className="text-gray-700 mt-2">
              Price: <span className="font-semibold">${product.price}</span>
            </p>
          </div>
        ))}
      </div>

      <dialog id="my_modal_1" className="modal">
        <div className="modal-box">
          <p className="py-4">
            <ProductForm sellerId={id} />
          </p>
          <div className="modal-action">
            <form method="dialog">
              <button className="btn">Close</button>
            </form>
          </div>
        </div>
      </dialog>
    </div>
  );
};

export default SellerPage;
