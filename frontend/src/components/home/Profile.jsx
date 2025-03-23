import React from "react";
import "../../Style/Profile.css";

const Profile = ({ user, boton }) => {
   
	return (
		<div className="profile-container">
			<div className="profile-card">
				<img src="/avatar.png" alt="Avatar" className="profile-avatar" />
				<h2 className="profile-name">{user.name}</h2>
				<p className="profile-email">{user.email}</p>
				<p className="profile-id">ID: {user.id}</p>
                {boton}
			</div>
			
		</div>
	);
};

export default Profile;
