document.addEventListener('DOMContentLoaded', () => {
    const grupeSelect = document.getElementById('grupeSelect');
    const artikliSelect = document.getElementById('artikliSelect');

    fetch('/api/grupe-artikala')
        .then(response => response.json())
        .then(data => {
            data.forEach(grupaArtikla => {
                const option = document.createElement('option');
                option.value = grupaArtikla.id;
                option.textContent = grupaArtikla.nazivGrupe;
                grupeSelect.appendChild(option);
            });
        });

    grupeSelect.addEventListener('change', () => {
        const grupaId = grupeSelect.value;
        console.log("Kliknuta grupa ID:", grupaId);
        artikliSelect.innerHTML = '<option disabled selected>-- Odaberite artikl --</option>';
        artikliSelect.disabled = true;

        fetch(`/api/artikli/grupa/${grupaId}`)
            .then(response => response.json())
            .then(artikli => {
                if (artikli.length > 0) {
                    artikli.forEach(artikl => {
                        const option = document.createElement('option');
                        option.value = artikl.id;
                        option.textContent = `${artikl.naziv} - ${artikl.cijena.toFixed(2)} €`;
                        artikliSelect.appendChild(option);
                    });
                    artikliSelect.disabled = false;
                } else {
                    artikliSelect.innerHTML = '<option disabled selected>-- Nema artikala --</option>';
                    artikliSelect.disabled = true;
                }
            })
            .catch(err => console.error('Greška pri dohvaćanju artikala:', err));
    });
});

function dodajArtikl() {
    const container = document.getElementById('artikliContainer');
    const trenutniBroj = container.querySelectorAll('.artikl').length;
    if (trenutniBroj + 1 >= 5) {
        document.getElementById('dodajArtikl').style.display = 'none';
    }
   if (trenutniBroj >= 5) {
       alert("Možete dodati najviše 5 artikala.");
       return;
   }

    const noviArtikl = document.createElement('div');
    noviArtikl.classList.add('artikl');

    noviArtikl.innerHTML = `
        <select class="grupeSelect" name="grupa[]" required>
            <option disabled selected>-- Odaberite grupu --</option>
        </select>
        <select class="artikliSelect" name="artikl[]" disabled required>
            <option disabled selected>-- Odaberite artikl --</option>
        </select>
        <input type="number" placeholder="Količina" name="kolicina[]" required min="1" />
        <div class="artiklDetalji"></div>
    `;

    container.appendChild(noviArtikl);

    const grupeSelect = noviArtikl.querySelector('.grupeSelect');
    const artikliSelect = noviArtikl.querySelector('.artikliSelect');

    fetch('/api/grupe-artikala')
        .then(response => response.json())
        .then(data => {
            data.forEach(grupaArtikla => {
                const option = document.createElement('option');
                option.value = grupaArtikla.id;
                option.textContent = grupaArtikla.nazivGrupe;
                grupeSelect.appendChild(option);
            });
        });
    grupeSelect.addEventListener('change', () => {
        const grupaId = grupeSelect.value;

        artikliSelect.innerHTML = '<option disabled selected>-- Odaberite artikl --</option>';
        artikliSelect.disabled = true;

        fetch(`/api/artikli/grupa/${grupaId}`)
            .then(response => response.json())
            .then(artikli => {
                if (artikli.length > 0) {
                    artikli.forEach(artikl => {
                        const option = document.createElement('option');
                        option.value = artikl.id;
                        option.textContent = `${artikl.naziv} - ${artikl.cijena.toFixed(2)} €`;
                        artikliSelect.appendChild(option);
                    });
                    artikliSelect.disabled = false;
                } else {
                    artikliSelect.innerHTML = '<option disabled selected>-- Nema artikala --</option>';
                    artikliSelect.disabled = true;
                }
            })
            .catch(err => console.error('Greška pri dohvaćanju artikala:', err));
    });
}
document.getElementById('dodajArtikl').addEventListener('click', dodajArtikl);


document.getElementById('narudzbaKupca').addEventListener('submit', function(e) {
    e.preventDefault();
    const ime = document.getElementById("ime").value.trim();
    const prezime = document.getElementById("prezime").value.trim();
    const kontakt = document.getElementById("kontakt").value.trim();
    const adresa = document.getElementById("adresa").value.trim();
    const mail = document.getElementById("mail").value.trim();

    const imePrezimeRegex = /^[A-ZČĆŽŠĐ][a-zčćžšđ]{1,19}(?:[-\s][A-ZČĆŽŠĐ][a-zčćžšđ]{1,19})?$/;
    const kontaktRegex = /^(\+385|0)\s?\d{2}\s?\d{3,4}\s?\d{3,4}$/;
    const adresaRegex = /^[A-ZČĆŽŠĐa-zčćžšđ0-9\s.,-/]{5,100}$/;
    const mailRegex = /^[a-zA-Z0-9._%+-]+@example\.com$/;

    document.getElementById("porukaIme").textContent = "";
    document.getElementById("porukaPrezime").textContent = "";
    document.getElementById("porukaKontakt").textContent = "";
    document.getElementById("porukaAdresa").textContent = "";
    document.getElementById("porukaMail").textContent = "";

    const nacinPlacanja = document.getElementById("nacinPlacanja").value;
    const zeliDostavu = document.getElementById("zeliDostavu").checked;
    const zeliOdgoduPlacanja = document.getElementById("zeliOdgoduPlacanja").checked;


    let valid = true;
    if (!imePrezimeRegex.test(ime)) {
        document.getElementById("porukaIme").textContent = "Ime nije ispravno.";
        valid = false;
    }
    if (!imePrezimeRegex.test(prezime)) {
        document.getElementById("porukaPrezime").textContent = "Prezime nije ispravno.";
        valid = false;
    }
    if (!kontaktRegex.test(kontakt)) {
        document.getElementById("porukaKontakt").textContent = "Kontakt broj nije ispravan. Format: +385xxxxxxxx ili 0xxxxxxxx.";
        valid = false;
    }
    if (!adresaRegex.test(adresa)) {
        document.getElementById("porukaAdresa").textContent = "Adresa nije ispravna.";
        valid = false;
    }
    if (!mailRegex.test(mail)) {
            document.getElementById("porukaMail").textContent = "Mail nije ispravan. Format xxx.xxxx@example.com";
            valid = false;
        }
    if (!nacinPlacanja) {
        document.getElementById("porukaPlacanje").textContent = "Molimo odaberite način plaćanja";
        valid = false;
    }

    const artikli = document.querySelectorAll('#artikliContainer .artikl');
    const narudzbaArtikli = [];

    artikli.forEach(el => {
        const grupaSelect = el.querySelector('[name="grupa[]"]');
        const artiklSelect = el.querySelector('[name="artikl[]"]');
        const kolicinaInput = el.querySelector('[name="kolicina[]"]');

        if (grupaSelect.value ==='-- Odaberite grupu --') {
            alert("Odaberite grupu za sve artikle!");
            valid = false;
        }

        if (!artiklSelect || artiklSelect.disabled || artiklSelect.value  ==='-- Odaberite artikl --') {
            alert("Odaberite artikl za sve artikle!");
            valid = false;
        }

        const kolicina = parseInt(kolicinaInput.value);
        if (!kolicina || kolicina <= 0) {
            alert("Unesite količinu veću od 0 za sve artikle!");
            valid = false;
        }

        if (valid) {
            const artiklNaziv = artiklSelect.selectedOptions[0].text.split(" - ")[0];
            narudzbaArtikli.push({
                grupa: grupaSelect.selectedOptions[0].text,
                artikl: artiklNaziv,
                kolicina: kolicina
            });
        }
    });

    if (!valid) return;



    const narudzba = {
        ime,
        prezime,
        kontakt,
        adresa,
        mail,
        tip_kupca: 4,
        artikli: narudzbaArtikli,
        nacinPlacanja: nacinPlacanja,
        zeliDostavu: zeliDostavu,
        zeliOdgoduPlacanja: zeliOdgoduPlacanja
    };


    fetch('/api/narudzbe', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(narudzba)
    }).then(res => {
        if (res.ok) {
            alert("Narudžba uspješno poslana!");
            document.getElementById('narudzbaKupca').reset();
        } else {
            alert("Greška pri slanju narudžbe!");
        }
    }).catch(() => {
        alert("Došlo je do pogreške prilikom slanja narudžbe!");
    });
});




const images = [
    "paterna-webshop-1.png",
    "boje.png",
    "materijal.png",
    "radovi.png",
    "plocice.png",
    "zgrada.png",
    "skladiste.png"
];

let index = 0;
const izmjenaSlika = document.getElementById("izmjenaSlika");

setInterval(() => {
    index = (index + 1) % images.length;
    izmjenaSlika.src = images[index];
}, 3000);