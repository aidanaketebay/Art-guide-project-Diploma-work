class UsersController < ApplicationController
	include SessionsHelper

	def index
		@users = User.all
	end

	def show
		@user = User.find(params[:id])
	end

	def new
		@user = User.new

	end

	def create
		@user  = User.new(user_params)
		if @user.save
			sign_in @user
			redirect_to @user
		else
			render :new
		end

	end

	def edit
		@user = User.find(params[:id])
	end
	
	def update
		@user = User.find(params[:id])
		if @user.update(user_params)
			redirect_to @user 
		else
			render 'edit'
		end
	end
	
	def destroy
	    user = User.find(params[:id])
	    sign_out if signed_in?
	    @user = user.destroy
			redirect_to users_url
	end
	
	def user_params 
		params.require(:user).permit(:username, :email,:password, :avatar)
	end

end
