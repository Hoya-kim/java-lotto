package lotto.domain;

import static lotto.util.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LottoTest {

    private Lotto lotto;

    @BeforeEach
    void setUp() {
        lotto = new Lotto();
    }

    @Test
    void 로또숫자들은_1부터45까지의_중복되지_않은_6개의_수들이다() {
        List<Integer> lottoNumbers = lotto.generateRandoms();
        long given = lottoNumbers.stream().distinct().count();
        assertThat(given).isEqualTo(LOTTO_SIZE);
    }

    @ValueSource(ints = {2, 3, 4})
    @ParameterizedTest
    void 구입금액에_맞는_로또를_발급한다(int number) {
        lotto.generateLottoTickets(number);
        int given = lotto.getNumOfTickets();
        assertThat(given).isEqualTo(number);
    }

    @Test
    void 구매단위는_1000원_이다() {
        int given = lotto.buyLottoTickets(10000);
        assertThat(given).isEqualTo(10);
    }

    @Test
    void 로또넘버중_당첨넘버와_일치하는_수에_따라_당첨결과가_나온다_1등() {
        final LottoNumbers lottoNumbers = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        final WinningNumbers winningNumbers = new WinningNumbers(Arrays.asList(1, 2, 3, 4, 5, 6),
            40);

        LottoRank given = lotto.compareWithWinning(lottoNumbers, winningNumbers);
        assertThat(given).isEqualTo(LottoRank.FIRST);
    }

    @Test
    void 로또넘버중_당첨넘버와_일치하는_수에_따라_당첨결과가_나온다_2등() {
        final LottoNumbers lottoNumbers = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        final WinningNumbers winningNumbers = new WinningNumbers(Arrays.asList(1, 2, 3, 4, 5, 7),
            6);

        LottoRank given = lotto.compareWithWinning(lottoNumbers, winningNumbers);
        assertThat(given).isEqualTo(LottoRank.SECOND);
    }
}