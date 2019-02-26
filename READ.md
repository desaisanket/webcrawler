Web Crawler
This web crawler accepts root domain name as input. It will scan through all child pages under that domain.
Result would be a json file output.json in src/main/resources directory.
I have already attached a sample output in the same directory which is formatted and easy to read.
Output will consist of all the child pages under that domain, internal links present in each page, external links, files referred to, images referred to.



Getting Started

To run the project execute the test class WebcrawlerApplicationTests in src/test/java.
Input should be the domain name without / e.g. https://www.prudential.co.uk