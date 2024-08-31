import React from "react";

interface User {
  id: number;
  username: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  address: string;
}

interface UserInfoCardProps {
  user: User;
}

const UserInfoCard: React.FC<UserInfoCardProps> = ({ user }) => {
  return (
    <div className="bg-white shadow-md rounded-lg p-4 max-w-sm mx-auto">
      <h2 className="text-xl font-semibold mb-3">User Information</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
        <div className="bg-gray-100 p-3 rounded-lg shadow-sm">
          <h3 className="text-base font-medium">Username</h3>
          <p className="text-gray-700 text-sm">{user.username}</p>
        </div>
        <div className="bg-gray-100 p-3 rounded-lg shadow-sm">
          <h3 className="text-base font-medium">Full Name</h3>
          <p className="text-gray-700 text-sm">{user.fullName}</p>
        </div>
        <div className="bg-gray-100 p-3 rounded-lg shadow-sm">
          <h3 className="text-base font-medium">Email</h3>
          <p className="text-gray-700 text-sm">{user.email}</p>
        </div>
        <div className="bg-gray-100 p-3 rounded-lg shadow-sm">
          <h3 className="text-base font-medium">Phone Number</h3>
          <p className="text-gray-700 text-sm">{user.phoneNumber}</p>
        </div>
        <div className="bg-gray-100 p-3 rounded-lg shadow-sm col-span-1 md:col-span-2">
          <h3 className="text-base font-medium">Address</h3>
          <p className="text-gray-700 text-sm">{user.address}</p>
        </div>
      </div>
    </div>
  );
};

export default UserInfoCard;
