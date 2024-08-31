import React, { useEffect } from "react";
import { useBlackListStore } from "../store/blackListStore";
import { useAuthStore } from "../store/authStore";
import { X } from "lucide-react";

const BlackListPage = () => {
  const { sellers, getBlackList, deleteBlacklist, isLoading, error } =
    useBlackListStore();
  const { user, setBlacklistSellers } = useAuthStore();
  const userId = user.id;

  useEffect(() => {
    getBlackList(userId);
  }, [getBlackList, userId]);

  const handleDeleteBlacklist = (sellerId: number) => {
    deleteBlacklist(userId, sellerId);
    setBlacklistSellers(sellerId);
  };

  console.log(sellers);

  if (isLoading) return <p className="text-center text-gray-700">Loading...</p>;
  if (error) return <p className="text-center text-red-500">Error: {error}</p>;

  return (
    <div className="max-w-screen-xl mx-auto p-4 md:p-8">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-900">
        Blacklisted Sellers
      </h1>
      {sellers.length === 0 ? (
        <p className="text-center text-gray-600">
          No sellers in your blacklist.
        </p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {sellers.map((seller) => (
            <div
              key={seller.id}
              className="bg-white rounded-lg shadow-md overflow-hidden transition-transform transform hover:scale-105 relative"
            >
              <button
                onClick={() => handleDeleteBlacklist(Number(seller.id))}
                className="absolute right-0 hover:bg-red-500"
              >
                <X />
              </button>

              <div className="flex justify-center items-center p-2">
                <img
                  src={`http://localhost:8080/files/${seller.logo}`}
                  alt={seller.companyName}
                  className="w-24 h-24 object-cover"
                />
              </div>
              <div className="p-4">
                <h2 className="text-lg font-semibold text-gray-800 mb-2">
                  {seller.companyName}
                </h2>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default BlackListPage;
