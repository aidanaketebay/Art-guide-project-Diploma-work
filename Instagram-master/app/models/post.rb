# == Schema Information
#
# Table name: posts
#
#  id          :integer          not null, primary key
#  body        :text
#  user_id     :integer
#  likes_count :integer          default(0)
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#

class Post < ApplicationRecord
	validates :body, length: {maximum: 255}
	validates :user_id, presence: true
	belongs_to :user
	has_one_attached :image
	has_many :likes

	default_scope -> {order "created_at DESC"}
	

	def liked?
		if signed_in?
			likes.where(user_id: current_user.id).count > 0
		end
	end
end
