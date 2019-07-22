#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'flutter_baidu_tts'
  s.version          = '0.0.1'
  s.summary          = 'A new Flutter plugin for Baidu TTS.'
  s.description      = <<-DESC
A new Flutter plugin for Baidu TTS.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.vendored_libraries = 'lib/libBaiduSpeechSDK.a'
  s.frameworks = 'GLKit', 'SystemConfiguration', 'AudioToolbox', 'AVFoundation', 'CFNetwork', 'CoreLocation', 'CoreTelephony'
  s.library = 'sqlite3.0', 'iconv.2', 'c++', 'z.1'
#  s.requires_arc = true
  s.ios.deployment_target = '8.0'
end

