const apiKey = 'pS2UrTSQaUinqkDWsuCq9%2BWXge1G%2FBtY5Buv8BSU0NUu8lrVoa18K3XiyP9Aw3v3jEbJ%2BmbaFqGysTiZelVklA%3D%3D';

var RE = 6371.00877; // 지구 반경(km)
var GRID = 5.0; // 격자 간격(km)
var SLAT1 = 30.0; // 투영 위도1(degree)
var SLAT2 = 60.0; // 투영 위도2(degree)
var OLON = 126.0; // 기준점 경도(degree)
var OLAT = 38.0; // 기준점 위도(degree)
var XO = 43; // 기준점 X좌표(GRID)
var YO = 136; // 기준점 Y좌표(GRID)

function success(position) {
   const lat = position.coords.latitude;
   const lon = position.coords.longitude;
   const currentDateTime = getCurrentDateTime();

   var grid = dfs_xy_conv("toXY", lat, lon);
   var url = new URL("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst");
   var params = {
     serviceKey: apiKey,
     pageNo: 1,
     numOfRows: 60,
     dataType: 'json',
     base_date: currentDateTime.date,
     base_time: currentDateTime.time,
     nx: grid.x,
     ny: grid.y
   };

   url.search = new URLSearchParams(params).toString();

   $.ajax({
       url: decodeURI(url),
       method: 'GET',
       dataType: 'json',
       success: function(data) {
           const dataList = data.response.body.items.item;
           console.log(data);

           var csrfHeader = $("meta[name='_csrf_header']").attr("content");
           var csrfToken = $("meta[name='_csrf']").attr("content");

           var weatherList = new Array();
           weatherList.push(dataList[6].fcstValue);
           weatherList.push(dataList[18].fcstValue);
           weatherList.push(dataList[24].fcstValue);

           $.ajax({
               url: '/recommend/weather',
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json',
                   [csrfHeader]: csrfToken,
               },
               data: JSON.stringify(weatherList),
               success: function(result) {
               console.log(result);
                   document.querySelector("#image1").src = result[0][0].image;
                   document.querySelector("#menu1").innerText = result[0][0].menu;
                   document.querySelector("#ex1").innerText = result[0][0].explanation;
                   document.querySelector("#image2").src = result[0][1].image;
                   document.querySelector("#menu2").innerText = result[0][1].menu;
                   document.querySelector("#ex2").innerText = result[0][1].explanation;
                   document.querySelector("#image3").src = result[0][2].image;
                   document.querySelector("#menu3").innerText = result[0][2].menu;
                   document.querySelector("#ex3").innerText = result[0][2].explanation;

                   if (result[1][0]) {
                      document.querySelector("#card1").style.display = "block";
                      document.querySelector("#rImage1").src = result[1][0].image;
                      document.querySelector("#rName1").innerText = result[1][0].name;
                      document.querySelector("#rName1").href = "/restaurant/detail/" + result[1][0].id;
                      document.querySelector("#rAddress1").innerText = result[1][0].address;
                   }

                   if (result[1][1]) {
                       document.querySelector("#card2").style.display = "block";
                       document.querySelector("#rImage2").src = result[1][1].image;
                       document.querySelector("#rName2").innerText = result[1][1].name;
                       document.querySelector("#rName2").href = "/restaurant/detail/" + result[1][1].id;
                       document.querySelector("#rAddress2").innerText = result[1][1].address;
                   }
                   if (result[1][2]) {
                       document.querySelector("#card3").style.display = "block";
                       document.querySelector("#rImage3").src = result[1][2].image;
                       document.querySelector("#rName3").innerText = result[1][2].name;
                       document.querySelector("#rName3").href = "/restaurant/detail/" + result[1][2].id;
                       document.querySelector("#rAddress3").innerText = result[1][2].address;
                   }

                   products = [result[0][0].menu, result[0][1].menu, result[0][2].menu];
                   newMake();
               },
               error: function(error) {
                   console.error('Error:', error);
               }
           });

     handleSkyData(dataList[18].fcstValue, dataList[6].fcstValue);
     handleTemperatureData(dataList[24].fcstValue);
     handlePrecipitationData(dataList[6].fcstValue);

     function handleSkyData(skyValue, precipitationValue) {
         const skyElement = document.querySelector("#sky");
         const skyImage = document.getElementById("skyStatus");

         if (![1, 2, 3, 4].includes(precipitationValue)) {
            if (skyValue == 1) {
                skyElement.innerText = "맑고"
                skyImage.src = "/image/weather/쨍쨍.png"
            } else if (skyValue == 3) {
                skyElement.innerText = "구름이 많고"
                skyImage.src = "/image/weather/구름많음.png"
            } else {
                skyElement.innerText = "흐리고"
                skyImage.src = "/image/weather/흐림.png"
            }
         }
     }

     function handleTemperatureData(tempValue) {
         const tempElement1 = document.querySelector("#temp1");
         const tempElement2 = document.querySelector("#temp2");
         const tempImage = document.getElementById("tempStatus");
         tempElement1.innerText = `${tempValue}℃`;
         if (tempValue < 0) {
             tempElement2.innerText = `매우 추운 날씨`;
             tempImage.src = "/image/weather/추워온도계.png";
         } else if (tempValue >= 0 && tempValue < 15) {
             tempElement2.innerText = `추운 날씨`;
             tempImage.src = "/image/weather/추운사람.png";
         } else if (tempValue >= 15 && tempValue < 20) {
             tempElement2.innerText = `선선한 날씨`;
             tempImage.src = "/image/weather/딱좋아온도계.png";
         } else if (tempValue >= 20 && tempValue < 25) {
             tempElement2.innerText = `따뜻한 날씨`;
             tempImage.src = "/image/weather/딱좋아온도계.png";
         } else if (tempValue >= 25 && tempValue < 30) {
             tempElement2.innerText = `더운 날씨`;
             tempImage.src = "/image/weather/더운사람.png";
         } else {
             tempElement2.innerText = `매우 더운 날씨`;
             tempImage.src = "/image/weather/더워온도계";
         }
     }

     function handlePrecipitationData(rainValue) {
         const rainElement = document.querySelector("#rain");
         const skyImage = document.getElementById("skyStatus");

         switch (rainValue) {
             case 1:
                 rainElement.innerText = "현재 비가 오고 있습니다.";
                 skyImage.src = "/image/weather/비.png";
                 break;
             case 2:
                 rainElement.innerText = "현재 비 또는 눈이 오고 있습니다.";
                 skyImage.src = "/image/weather/눈이나 비.png";
                 break;
             case 3:
                 rainElement.innerText = "현재 눈이 오고 있습니다.";
                 skyImage.src = "/image/weather/눈.png";
                 break;
         }
     }

   },
   error: function(err) {
           console.log('Ajax Error', err);
   }

});
}

function error() {
   alert("위치 정보를 불러올 수 없습니다.");
}

navigator.geolocation.getCurrentPosition(success, error);

function add30Minutes(timeString) {
    const hours = parseInt(timeString.substring(0, 2), 10);
    const minutes = parseInt(timeString.substring(2), 10);

    const newHours = hours + Math.floor((minutes + 30) / 60);
    const newMinutes = (minutes + 30) % 60;

    const newTimeString = `${newHours.toString().padStart(2, '0')}${newMinutes.toString().padStart(2, '0')}`;

    return newTimeString;
}

function getCurrentDateTime() {
    const now = new Date();

    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const day = now.getDate().toString().padStart(2, '0');
    let hours = now.getHours();
    let minutes = '30';

    if (now.getMinutes() < 30) {
        hours -= 1;
    }

    if (hours < 0) {
        hours = '00';
    } else {
        hours = hours.toString().padStart(2, '0');
    }

    return {
        date: `${year}${month}${day}`,
        time: `${hours}${minutes}`
    };
}

function roundDownToHalfHour(date) {
    const roundedDate = new Date(date);
    roundedDate.setMinutes(roundedDate.getMinutes() - (roundedDate.getMinutes() % 30));
    return roundedDate;
}


function dfs_xy_conv(code, v1, v2) {
    var DEGRAD = Math.PI / 180.0;
    var RADDEG = 180.0 / Math.PI;

    var re = RE / GRID;
    var slat1 = SLAT1 * DEGRAD;
    var slat2 = SLAT2 * DEGRAD;
    var olon = OLON * DEGRAD;
    var olat = OLAT * DEGRAD;

    var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
    var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
    var ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
    ro = re * sf / Math.pow(ro, sn);
    var rs = {};
    if (code == "toXY") {
        rs['lat'] = v1;
        rs['lng'] = v2;
        var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        var theta = v2 * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs['x'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs['y'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
    }
    else {
        rs['x'] = v1;
        rs['y'] = v2;
        var xn = v1 - XO;
        var yn = ro - v2 + YO;
        ra = Math.sqrt(xn * xn + yn * yn);
        if (sn < 0.0) - ra;
        var alat = Math.pow((re * sf / ra), (1.0 / sn));
        alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

        if (Math.abs(xn) <= 0.0) {
            theta = 0.0;
        }
        else {
            if (Math.abs(yn) <= 0.0) {
                theta = Math.PI * 0.5;
                if (xn < 0.0) - theta;
            }
            else theta = Math.atan2(xn, yn);
        }
        var alon = theta / sn + olon;
        rs['lat'] = alat * RADDEG;
        rs['lng'] = alon * RADDEG;
    }
    return rs;
}

const newMake = () => {
        const [cw, ch] = [$c.width / 2, $c.height / 2];
        const arc = Math.PI * 2 / products.length;

        for (let i = 0; i < products.length; i++) {
            ctx.beginPath();
            if (colors.length === 0) {
                generateRandomColors();
            }
            ctx.fillStyle = colors[i % colors.length];
            ctx.moveTo(cw, ch);
            ctx.arc(cw, ch, cw - 20, arc * i, arc * (i + 1));
            ctx.fill();
            ctx.closePath();
        }

        ctx.fillStyle = "#000";
        ctx.font = "18px IBM Plex Sans KR";
        ctx.textAlign = "center";

        for (let i = 0; i < products.length; i++) {
            const angle = arc * i + arc / 2;

            ctx.save();

            ctx.translate(
                cw + Math.cos(angle) * (cw - 60),
                ch + Math.sin(angle) * (ch - 60)
            );

            ctx.rotate(angle + Math.PI / 2);

            products[i].split(" ").forEach((text, j) => {
                ctx.fillText(text, 0, 30 * j);
            });

            ctx.restore();
        }
    };

    const rotate = () => {
        const rouletteDiv = document.getElementById("roulette");
        $c.style.transform = `initial`;
        $c.style.transition = `initial`;

        setTimeout(() => {
            const ran = Math.floor(Math.random() * products.length);
            const arc = Math.PI * 2 / products.length;
            const rotate = ran * arc + arc * 5;

            $c.style.transform = `rotate(${rotate}rad)`;
            $c.style.transition = `2s`;

            const resultElement = document.getElementById('result');
            let result;

            if (parseInt(products.length * 2 - rotate) < 0) result = products.length - 1;
            else result = parseInt(products.length * 2 - rotate);
            resultElement.classList.add('animate__bounceOutDown');
            resultElement.addEventListener('animationend', () => {
                resultElement.classList.remove('animate__bounceOutDown');
            }, { once: true });
        }, 3);
    };

    function generateRandomColors() {
        const predefinedColors = [
            'linen',
            'antiquewhite',
            'bisque',
            'peachpuff',
            'burlywood',
            'tan',
            'wheat',
            'snow',
            'lightyellow',
            'navajowhite',
            'sandybrown',
            'peru',
            'saddlebrown',
            'sienna',
            'moccasin'
        ];

        colors.length = 0;

        for (let l = 0; l < products.length; l++) {
            let randomIndex = Math.floor(Math.random() * predefinedColors.length);
            let selectedColor = predefinedColors[randomIndex];

            colors.push(selectedColor);
        }
    }

        const addRandomMenu = () => {
            products = shuffleArray(allProducts).slice(0, initialDisplayCount);

            newMake();
        };

        const shuffleArray = (array) => {
            for (let i = array.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [array[i], array[j]] = [array[j], array[i]];
            }
            return array;
        };

        const resetRoulettePosition = () => {
        const rouletteDiv = document.getElementById("roulette");

        rouletteDiv.classList.remove('animate__slideOutLeft');

        rouletteDiv.style.transform = 'initial';
    };