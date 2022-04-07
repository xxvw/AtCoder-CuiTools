sudo mkdir /usr/local/bin/atc
sudo cp -v atc.jar /usr/local/bin/atc
echo "alias atc \"java -jar /usr/local/bin/atc/atc.jar $argv\"" >> ~/.config/fish/config.fish
