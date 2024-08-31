import { ReactNode } from "react";

interface FilterButtonProps {
  children: ReactNode;
  onClick: () => void;
}

const FilterButton = ({ children, onClick }: FilterButtonProps) => {
  return (
    <button
      onClick={onClick}
      className="relative flex items-center gap-2 px-4 py-2 bg-gray-700 text-white rounded-md border-2 border-transparent hover:bg-gray-600 hover:border-gray-500 focus:outline-none focus:ring-2 focus:ring-gray-400 focus:ring-opacity-50"
    >
      {children}
    </button>
  );
};

export default FilterButton;
