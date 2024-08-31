import React from "react";

interface ProductProps {
  name: string;
  logo: string;
  description: string;
  price: number;
}

interface ProductCardProps {
  product: ProductProps;
}

const Product: React.FC<ProductCardProps> = ({ product }) => {
  return (
    <div className="border p-4 rounded-lg shadow-sm">
      <img
        src={product.logo}
        alt={product.name}
        className="w-full h-32 object-cover mb-4"
      />
      <h2 className="text-lg font-semibold">{product.name}</h2>
      <p>{product.description}</p>
      <p className="mt-2 text-green-600">${product.price}</p>
    </div>
  );
};

export default Product;
