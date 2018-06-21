module PagesHelper
	def pretty_title(title)
		#"| " + title unless title.nil? 
		title.nil? ? "Instagram " : "| " + title
	end
end
