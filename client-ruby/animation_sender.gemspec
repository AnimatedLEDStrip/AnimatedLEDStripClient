Gem::Specification.new do |s|
  s.name = 'animatedledstrip-client'
  s.version = '0.0.0'
  s.date = '2019-11-05'
  s.authors = ['Max Narvaez']
  s.summary = 'Library for connecting to an AnimatedLEDStripServer'
  s.files = %w[lib/animation.rb
               lib/animation_data.rb
               lib/animation_sender.rb
               lib/color_container.rb
               lib/direction.rb]
  s.require_paths = ['lib']
  s.test_files = %w[test/test_animation.rb
                    test/test_animation_data.rb
                    test/test_animation_sender.rb
                    test/test_direction.rb]

  s.add_dependency 'json'
  s.add_dependency 'simplecov'
  s.add_dependency 'url'

  s.add_development_dependency 'minitest'
  s.add_development_dependency 'mocha'
  s.add_development_dependency 'rake'
end
