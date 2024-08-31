import { ArrowRight, Search } from "lucide-react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import UserCard from "../components/user/UserCard";
import UserInfoCard from "../components/user/UserInfoCard";
import { useAuthStore } from "../store/authStore";
import { useProductStore } from "../store/productStore";
import { useSellerStore } from "../store/sellerStore";
import { useUserStore } from "../store/userStore";

const AdminPage = () => {
  const { user } = useAuthStore();
  const { searchUsers, getUsers, users } = useUserStore();
  const { products, getProducts, searchProducts } = useProductStore();
  const { getSellers, sellers, searchSellers } = useSellerStore();
  const [searchQuery, setSearchQuery] = useState("");
  const [searchUserQuery, setSearchUserQuery] = useState("");
  const [searchSellerQuery, setSearchSellerQuery] = useState("");

  const handleSearch = (e) => {
    e.preventDefault();
    searchProducts(searchQuery, 0, 3);
  };

  const handleUserSearch = (e) => {
    e.preventDefault();
    searchUsers(searchUserQuery);
  };

  const handleSellerSearch = (e) => {
    e.preventDefault();
    searchSellers(searchSellerQuery, 0, 3);
  };

  useEffect(() => {
    getProducts(0, 3);
    getUsers(0, 3);
    getSellers(0, 3);
  }, [getProducts, getUsers, getSellers]);

  return (
    <div className="max-w-[1200px] w-full md:mx-auto mt-8 m-5">
      <h1 className="text-2xl font-semibold text-center">Admin Page</h1>
      <UserInfoCard user={user} />

      {user?.isAdmin && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-5">
          {/* Products Section */}
          <div className="flex flex-col items-center w-full">
            <p className="text-xl font-semibold mb-2">Products</p>
            <form
              onSubmit={handleSearch}
              className="flex items-center bg-white rounded-lg shadow-sm overflow-hidden mb-4 w-full"
            >
              <input
                type="text"
                placeholder="Type the product name or category"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="py-2 px-3 border-none outline-none text-sm md:text-base flex-grow placeholder:text-xs"
              />
              <button
                type="submit"
                className="flex items-center justify-center bg-gray-100 p-2"
              >
                <Search className="text-gray-600" />
              </button>
            </form>
            <div className="grid grid-cols-1 gap-4 w-full">
              {products.map((product) => (
                <div
                  key={product.id}
                  className="bg-white shadow-md p-4 rounded-lg w-full"
                >
                  <div className="flex items-center justify-between">
                    <h1 className="text-lg font-semibold">{product.name}</h1>
                    <Link className="" to={`/product/${product.id}`}>
                      <ArrowRight />
                    </Link>
                  </div>

                  <p className="text-sm text-gray-500">{product.description}</p>
                  <p className="text-sm font-semibold text-gray-600">
                    ${product.price}
                  </p>

                  <div className="flex flex-wrap gap-1 mt-2">
                    {product.categories.map((category) => (
                      <span
                        key={category.id}
                        className="text-xs text-white bg-gradient-to-r from-blue-500 to-purple-600 px-2 py-1 rounded-full shadow-md"
                      >
                        {category}
                      </span>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Users Section */}
          <div className="flex flex-col items-center w-full">
            <p className="text-xl font-semibold mb-2">Users</p>
            <form
              onSubmit={handleUserSearch}
              className="flex items-center bg-white rounded-lg shadow-sm overflow-hidden mb-4 w-full"
            >
              <input
                type="text"
                placeholder="Type the username or email"
                value={searchUserQuery}
                onChange={(e) => setSearchUserQuery(e.target.value)}
                className="py-2 px-3 border-none outline-none text-sm md:text-base flex-grow placeholder:text-xs"
              />
              <button
                type="submit"
                className="flex items-center justify-center bg-gray-100 p-2"
              >
                <Search className="text-gray-600" />
              </button>
            </form>
            <div className="grid grid-cols-1 gap-4 w-full">
              {users.map((user) => (
                <UserCard key={user.id} user={user} />
              ))}
            </div>
          </div>

          <div className="flex flex-col items-center w-full">
            <div className="flex flex-col items-center w-full">
              <p className="text-xl font-semibold mb-2">Sellers</p>
              <form
                onSubmit={handleSellerSearch}
                className="flex items-center bg-white rounded-lg shadow-sm overflow-hidden mb-4 w-full"
              >
                <input
                  type="text"
                  placeholder="Type the username or email"
                  value={searchSellerQuery}
                  onChange={(e) => setSearchSellerQuery(e.target.value)}
                  className="py-2 px-3 border-none outline-none text-sm md:text-base flex-grow placeholder:text-xs"
                />
                <button
                  type="submit"
                  className="flex items-center justify-center bg-gray-100 p-2"
                >
                  <Search className="text-gray-600" />
                </button>
              </form>
              {sellers.map((seller) => (
                <div
                  key={seller.id}
                  className="bg-white shadow-md p-4 rounded-lg w-full mb-4"
                >
                  <div className="flex items-center justify-between">
                    <h1 className="text-lg font-semibold">
                      {seller.companyName}
                    </h1>
                    <Link className="" to={`/seller/${seller.id}`}>
                      <ArrowRight />
                    </Link>
                  </div>
                  <p className="text-sm text-gray-500">
                    {seller.contactNumber}
                  </p>
                  <p className="text-sm text-gray-500">{seller.websiteUrl}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminPage;
