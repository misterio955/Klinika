-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 09 Kwi 2018, 14:27
-- Wersja serwera: 5.6.21
-- Wersja PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `baza_klinika`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `lekarze`
--

CREATE TABLE IF NOT EXISTS `lekarze` (
`ID` int(11) NOT NULL,
  `Imie` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Nazwisko` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Haslo` varchar(16) COLLATE utf8_polish_ci NOT NULL,
  `Specjalizacja` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `Telefon` varchar(9) COLLATE utf8_polish_ci NOT NULL,
  `Sala` int(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `lekarze`
--

INSERT INTO `lekarze` (`ID`, `Imie`, `Nazwisko`, `Haslo`, `Specjalizacja`, `Telefon`, `Sala`) VALUES
(1, 'Janusz', 'Korwin', 'jkorwin', 'Urolog', '541238401', 4),
(2, 'Zbigniew', 'Kowalski', 'zkowalski', 'Dentysta', '886124793', 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pacjenci`
--

CREATE TABLE IF NOT EXISTS `pacjenci` (
`ID` int(11) NOT NULL,
  `Pesel` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `Imie` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Nazwisko` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Telefon` varchar(50) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `pacjenci`
--

INSERT INTO `pacjenci` (`ID`, `Pesel`, `Imie`, `Nazwisko`, `Telefon`) VALUES
(1, '84112300687', 'Zaneta', 'Chowaniec', '554129743'),
(2, '80012236149', 'Bogdan', 'Skucha', '801704665'),
(3, '78031541367', 'Barbara', 'Bielan', '701654901'),
(4, '98120622313', 'Rafal', 'Dziura', '541368712');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wizyty`
--

CREATE TABLE IF NOT EXISTS `wizyty` (
`ID` int(11) NOT NULL,
  `ID_Lekarza` int(11) NOT NULL,
  `ID_Pacjeta` int(11) NOT NULL,
  `Data_Wizyty` datetime NOT NULL,
  `Status_wizyty` varchar(100) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `wizyty`
--

INSERT INTO `wizyty` (`ID`, `ID_Lekarza`, `ID_Pacjenta`, `Data_Wizyty`, `Status_wizyty`) VALUES
(1, 1, 3, '2018-04-16 10:00:00', 'Zakonczona'),
(2, 2, 1, '2018-04-16 10:15:00', 'Przelozona'),
(3, 2, 1, '2018-06-19 11:00:00', 'Oczekiwana'),
(4, 2, 4, '2018-06-05 14:00:00', 'Oczekiwana'),
(5, 1, 3, '2018-04-06 09:00:00', 'Nie odbyla sie'),
(6, 1, 2, '2018-04-25 10:00:00', 'Anulowana');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `lekarze`
--
ALTER TABLE `lekarze`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `pacjenci`
--
ALTER TABLE `pacjenci`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `wizyty`
--
ALTER TABLE `wizyty`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `lekarze`
--
ALTER TABLE `lekarze`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `pacjenci`
--
ALTER TABLE `pacjenci`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT dla tabeli `wizyty`
--
ALTER TABLE `wizyty`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
