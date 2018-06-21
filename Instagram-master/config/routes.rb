Rails.application.routes.draw do
  get 'posts/index'
  get 'posts/new'
  get 'posts/update'
  get 'posts/delete'
  match "/contact", to: "pages#contact", via: :get
  get "/about", to: "pages#about"
  root "pages#index"
  resources :users
 
  #resources :sessions, only: [:new,:create,:destroy]
  get "/login", to: "sessions#new"
   get "/signup", to: "users#new"
  post "/login", to: "sessions#create"
  delete "/logout", to: "sessions#destroy"

  resources :posts, only: [:create, :destroy]
  resources :likes, only: [:create]
  delete "/unlike", to: "likes#destroy"

end
