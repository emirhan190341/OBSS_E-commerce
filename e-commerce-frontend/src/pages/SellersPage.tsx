import React, { useEffect, useState } from "react";
import { useSellerStore } from "../store/sellerStore";
import RotatedText from "../components/decorators/RotatedText";
import SellerCard from "../components/seller/SellerCard";
import FilterButton from "../components/decorators/FilterButton";
import { ArrowDown } from "lucide-react";
import SellerForm from "../components/seller/SellerForm";
import { useAuthStore } from "../store/authStore";

const SellersPage = () => {
  const { sellers, getSellers, getSellersSortedByDescRating, isLastPage } =
    useSellerStore();
  const { user } = useAuthStore();
  console.log(user);
  const [pageNumber, setPageNumber] = useState(0);

  const pageSize = 6;

  useEffect(() => {
    getSellers(pageNumber, pageSize);
  }, [getSellers, pageNumber]);

  const handleNextPage = () => {
    setPageNumber((prevPageNumber) => prevPageNumber + 1);
  };

  const handlePreviousPage = () => {
    if (pageNumber > 0) {
      setPageNumber((prevPageNumber) => prevPageNumber - 1);
    }
  };

  return (
    <div className="max-w-screen-xl mx-auto p-4 md:p-8">
      <h1 className="text-3xl font-extrabold text-gray-900 text-center mb-8">
        <RotatedText>SELLERS</RotatedText>
      </h1>

      <div className="flex justify-end  gap-4 mb-6">
        <FilterButton
          onClick={() => getSellersSortedByDescRating(0, 12, "desc")}
        >
          <ArrowDown className="w-4 h-4" />
          Sort by Rating
        </FilterButton>
        {user?.isAdmin && (
          <button
            className="btn"
            onClick={() => document.getElementById("my_modal_1").showModal()}
          >
            ADD NEW SELLER
          </button>
        )}
      </div>

      <div className="border rounded-xl mb-10">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 m-2">
          {sellers.map((seller) => (
            <SellerCard key={seller.id} seller={seller} />
          ))}
        </div>
      </div>

      <div className="flex justify-center items-center  mt-8 gap-3">
        <button
          onClick={handlePreviousPage}
          disabled={pageNumber === 0}
          className=" px-4 py-2 bg-gray-800 text-white rounded disabled:opacity-50"
        >
          Previous
        </button>

        <span className="text-gray-800 font-semibold">
          Page {pageNumber + 1}
        </span>

        <button
          onClick={handleNextPage}
          className="px-4 py-2 bg-gray-800 text-white rounded disabled:opacity-50"
          disabled={sellers.length < pageSize || isLastPage}
        >
          Next
        </button>
      </div>

      <dialog id="my_modal_1" className="modal">
        <div className="modal-box">
          <p className="py-4">
            <SellerForm />
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

export default SellersPage;
