INSERT INTO CityName (zipCode, cityName)
SELECT  DISTINCT POSTNR, BYNAVN FROM [dbo].[regionsopdelt-postnumme-pr.-22-06-2021]