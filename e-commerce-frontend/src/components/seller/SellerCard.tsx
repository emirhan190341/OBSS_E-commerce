import { Trash, X } from "lucide-react";
import React from "react";
import { useAuthStore } from "../../store/authStore";
import { useBlackListStore } from "../../store/blackListStore";
import { Link } from "react-router-dom";
import { useProductStore } from "../../store/productStore";
import { useSellerStore } from "../../store/sellerStore";

interface seller {
  id: number;
  companyName: string;
  address: string;
  contactNumber: string;
  email: string;
  websiteUrl: string;
  logo: string;
  rating: number;
  isVerified: boolean;
}

interface SearchCardProps {
  seller: seller;
}

const SellerCard: React.FC<SearchCardProps> = ({ seller }) => {
  const { user, setBlacklistSellers } = useAuthStore();
  const { removeProduct } = useProductStore();
  const { addBlacklist } = useBlackListStore();
  const { removeSeller } = useSellerStore();

  const handleAddBlacklist = () => {
    addBlacklist(user.id, seller.id);
    removeProduct(seller.id);
    setBlacklistSellers(seller.id);
  };

  const handleDeleteClick = () => {
    removeSeller(seller.id);
    removeProduct(seller.id);
  };

  return (
    <div className="bg-white shadow-md rounded-lg p-6 m-2 transition-transform transform hover:scale-105">
      <div className="flex flex-col items-center mb-4">
        {user && (
          <button
            onClick={handleAddBlacklist}
            className="absolute top-2 right-2 p-2 bg-white rounded-full shadow-md hover:bg-gray-100 flex items-center text-xs"
          >
            <X className="text-red-500" />{" "}
            {user.listOfBlacklistedSellers.includes(seller.id)
              ? "Blacklisted"
              : "Blacklist"}
          </button>
        )}

        {user?.isAdmin && (
          <button
            onClick={handleDeleteClick}
            className="absolute top-2 left-2 p-2 bg-white rounded-full shadow-md hover:bg-gray-100 flex items-center text-xs"
          >
            <Trash className="text-red-500 fill-red-400" />
          </button>
        )}

        <img
          src={`http://localhost:8080/files/${seller.logo}`}
          alt={seller.companyName}
          className="w-32 h-32 object-fit"
        />

        <div>
          <h2 className="text-xl font-bold text-gray-800">
            {seller.companyName}
          </h2>
          {seller.isVerified && (
            <span className="inline-block bg-green-500 text-white text-xs px-2 py-1 rounded-full ml-2">
              Verified
            </span>
          )}
        </div>
      </div>
      <div className="grid grid-cols-1 gap-2">
        <div>
          <h3 className="text-sm font-semibold text-gray-700">Address:</h3>
          <p className="text-sm text-gray-500">{seller.address}</p>
        </div>
        <div>
          <h3 className="text-sm font-semibold text-gray-700">Contact:</h3>
          <p className="text-sm text-gray-500">{seller.contactNumber}</p>
        </div>
        <div>
          <h3 className="text-sm font-semibold text-gray-700">Email:</h3>
          <p className="text-sm text-gray-500">{seller.email}</p>
        </div>
        <div>
          <h3 className="text-sm font-semibold text-gray-700">Website:</h3>
          <a
            href={seller.websiteUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-sm text-blue-500 hover:underline"
          >
            {seller.websiteUrl}
          </a>
        </div>
        <div className="flex justify-between items-center">
          <div className="flex items-center">
            <h3 className="text-sm font-semibold text-gray-700">Rating:</h3>
            <p className="text-sm text-yellow-500 ml-2">{seller.rating} ★</p>
          </div>

          <Link
            to={`/seller/${seller.id}`}
            className="text-sm bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
          >
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SellerCard;
