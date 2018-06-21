class User < ApplicationRecord
    has_secure_password

	has_many_attached :posts
    has_one_attached :avatar
    validates_presence_of :email, :username
    validates_uniqueness_of :email, :username
    validates :password, length: {minimum: 6, maximum: 39}
    validates_format_of :email, :with => /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\Z/i, :on => :update

    #validates_format_of :email, message: 'The e-mail format is not correct!'
    validates :username, :password, format: { with: /\A[0-9a-zA-Z_.\- ]+\Z/, message: "Only alphanumeric characters, spaces and -_."}
    validates :username, length: {maximum: 30}

    before_create {self.email = email.downcase}
	before_create {self.username = username.downcase}

    attr_accessor :remember_token
    def remember
        self.remember_token = User.new_token
        update_attribute(:remember_digest, User.digest(remember_token))
    end

    # Returns the hash digest of the given string.
	def User.digest(string)
        cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :
                                                  BCrypt::Engine.cost
        BCrypt::Password.create(string, cost: cost)
    end

  # Returns a random token.
    def User.new_token
        SecureRandom.urlsafe_base64
    end
      # Returns true if the given token matches the digest.
  def authenticated?(remember_token)
        if remember_digest.nil?
            false
        else
            BCrypt::Password.new(remember_digest).is_password?(remember_token)
        end
  end
  # Forgets a user.
  def forget
    update_attribute(:remember_digest, nil)
  end
end