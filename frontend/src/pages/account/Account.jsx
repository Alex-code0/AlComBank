import React, { useEffect, useState } from 'react';
import './account.css';
import { use } from 'react';

const Account = () => {
  const [account, setAccount] = useState(null);
  const [recipient, setTransferCardNumber] = useState('');
  const [transferAmount, setTransferAmount] = useState('');
  const [newCardNumber, setNewCardNumber] = useState(null);
  const [expireYear, setExpireYear] = useState('');
  const [expireMonth, setExpireMonth] = useState('');
  const [error, setError] = useState('');

  const handleTransferCardNumberChange = (e) => setTransferCardNumber(e.target.value);
  const handleTransferAmountChange = (e) => setTransferAmount(e.target.value);

  const handleNewCardNumberChange = (e) => setNewCardNumber(e.target.value);
  const handleExpireYearChange = (e) => {
    const value = e.target.value;
    if (value.length <= 2) {
      setExpireYear(value);
      if (value.length === 2) {
        document.getElementById('expire-month').focus();
      }
    }
  };

  const handleExpireMonthChange = (e) => setExpireMonth(e.target.value);

  useEffect(() => {
    const accountData = JSON.parse(localStorage.getItem('accountData'));
    setAccount(accountData);
  }, []);

  const handleCardChange = async (e) => {
    e.preventDefault();

    if (!newCardNumber || newCardNumber.length !== 16) {
      return;
    }

    const newCardExpireDate = `${expireMonth}/${expireYear}`;

    const newCardData = { accountId: account?.id, newCardNumber, newCardExpireDate};

    try {
      const response = await fetch('http://localhost:8080/api/updateCardDetails', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newCardData),
      });

      const data = await response.json();

      if (response.ok) {
        const updatedAccount = { ...account, cardNumber: data.cardNumber, expiredDate: data.cardExpireDate };
        setAccount(updatedAccount);
        localStorage.setItem('accountData', JSON.stringify(updatedAccount));
        alert("Transfer successful");
        window.location.reload();
      } else {
        console.log("Something went wrong")
      }
    } catch (error) {
      console.error("Error: ", error)
    }
  }

  const handleTransfer = async (e) => {
    e.preventDefault();
    setError('');

    if (!recipient || recipient.length !== 16) {
      setError('Invalid recipient card number');
      return;
    }

    const sender = account?.cardNumber;
    const transferData = { sender, recipient, transferAmount };

    try {
      const response = await fetch('http://localhost:8080/api/transfer', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(transferData),
      });

      const responseData = await response.text();

      if (response.ok) {
        alert('Transfer successful');
        const updatedAccount = { ...account, balance: responseData };
        setAccount(updatedAccount);
        localStorage.setItem('accountData', JSON.stringify(updatedAccount));
        alert("New card added");
        window.location.reload();
      } else {
        setError(responseData.message || 'Transfer failed');
      }
    } catch (error) {
      setError('An error occurred while processing the transfer');
      console.error('Error: ', error);
    }
  };

  return (
    <div className="account-container">
      <div className="account-details">
        <h2>Account Details</h2>
        {account ? (
          <>
            <p>
              <strong>Balance:</strong> ${account.balance}
            </p>
            <p>
              <strong>Card Number:</strong> {account.cardNumber || 'N/A'}
            </p>
            <p>
              <strong>Expire Date:</strong> {account.expireDate || 'N/A'}
            </p>
            <form onSubmit={handleTransfer}>
              <input
                type="text"
                placeholder="Recipient Card Number"
                value={recipient}
                onChange={handleTransferCardNumberChange}
                maxLength="16"
              />
              <input
                type="number"
                placeholder="Amount to transfer"
                value={transferAmount}
                onChange={handleTransferAmountChange}
                min="0"
              />
              <button type="submit">Transfer</button>
            </form>
            <form onSubmit={handleCardChange}>
              <div>
                <input
                  type="text"
                  maxLength="16"
                  placeholder="Card number"
                  value={newCardNumber}
                  onChange={handleNewCardNumberChange}
                />
                <label>Expire Date (MM/YY):</label>
                <input
                  type="text"
                  maxLength="2"
                  placeholder="YY"
                  value={expireYear}
                  onChange={handleExpireYearChange}
                />
                /
                <input
                  id="expire-month"
                  type="text"
                  maxLength="2"
                  placeholder="MM"
                  value={expireMonth}
                  onChange={handleExpireMonthChange}
                />
                <button type="submit">Update card information</button>
              </div>
            </form>
            {error && <p className="error-message">{error}</p>}
          </>
        ) : (
          <p>Loading account details...</p>
        )}
      </div>
    </div>
  );
};

export default Account;