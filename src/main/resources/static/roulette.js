    const $c = document.getElementById("roulette");
        const ctx = $c.getContext("2d");
        let allProducts = [$('#menu1').text(), $('#menu2').text(), $('#menu3').text()];
        let products = [];
        const colors = [];

        const initialDisplayCount = 3;

        window.onload = () => {
            products = allProducts.slice(0, initialDisplayCount);
            newMake();
        };

    function showRoulette() {
        document.getElementById('food-recommendation').style.display = 'none';
        document.getElementById('menu').style.display = 'flex';
        newMake();
        randomRecommendationBtn.style.display = 'none';
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

        ctx.fillStyle = "#fff";
        ctx.font = "18px Pretendard";
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
        colors.length = 0;
        for (let l = 0; l < products.length; l++) {
            let a = Math.floor(Math.random() * 256);
            let b = Math.floor(Math.random() * 256);
            let c = Math.floor(Math.random() * 256);
            colors.push(`rgb(${a},${b},${c})`);
        }
    }


        // 셔플 버튼 클릭 시 배열을 섞고, 처음 5개의 값만 보여주는 함수
        const addRandomMenu = () => {
            // 모든 값에서 무작위로 선택한 5개의 값을 products에 저장
            products = shuffleArray(allProducts).slice(0, initialDisplayCount);

            // 결과를 콘솔에 출력 (테스트용)
            console.log("셔플된 배열:", products);

            // 화면 갱신을 위해 새로운 돌림판 생성
            newMake();
        };

        // 배열을 무작위로 섞는 함수
        const shuffleArray = (array) => {
            for (let i = array.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [array[i], array[j]] = [array[j], array[i]];
            }
            return array;
        };

        const resetRoulettePosition = () => {
        const rouletteDiv = document.getElementById("roulette");

        // 애니메이션 클래스 제거
        rouletteDiv.classList.remove('animate__slideOutLeft');

        // 초기 위치로 돌아가는 스타일 적용
        rouletteDiv.style.transform = 'initial';
    };