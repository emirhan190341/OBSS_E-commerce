import { useEffect, useState } from "react";
import RotatedText from "../components/decorators/RotatedText";
import ProductCard from "../components/product/ProductCard";
import { useProductStore } from "../store/productStore";
import { ArrowDown, ArrowUp, Calendar } from "lucide-react";
import FilterButton from "../components/decorators/FilterButton";
import { useAuthStore } from "../store/authStore";

const ProductsPage = () => {
  const {
    products,
    searchProductsWithoutBlacklisted,
    getProductsSortedByDescPrice,
    isLastPage,
  } = useProductStore();
  const { user } = useAuthStore();
  const [pageNumber, setPageNumber] = useState(0);
  const pageSize = 6;

  useEffect(() => {
    searchProductsWithoutBlacklisted(user?.id, "", pageNumber, pageSize);
  }, [searchProductsWithoutBlacklisted, pageNumber]);

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
        <RotatedText>PRODUCTS</RotatedText>
      </h1>

      <div className="flex justify-center gap-4 mb-6">
        <FilterButton
          onClick={() => getProductsSortedByDescPrice(0, 12, "desc")}
        >
          <ArrowDown className="w-4 h-4" />
          Sort by Desc
        </FilterButton>

        <FilterButton
          onClick={() => getProductsSortedByDescPrice(0, 12, "asc")}
        >
          <ArrowUp className="w-4 h-4" />
          Sort by Asc
        </FilterButton>

        <FilterButton
          onClick={() => getProductsSortedByDescPrice(0, 12, "createdAt")}
        >
          <Calendar className="w-4 h-4" />
          Sort by Date
        </FilterButton>
      </div>

      <div className="border rounded-xl mb-10">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 m-2">
          {products.map((product) => (
            <ProductCard key={product._id} product={product} />
          ))}
        </div>
      </div>

      <div className="flex justify-center items-center mt-8 gap-3">
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
          disabled={products.length < pageSize || isLastPage}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default ProductsPage;
